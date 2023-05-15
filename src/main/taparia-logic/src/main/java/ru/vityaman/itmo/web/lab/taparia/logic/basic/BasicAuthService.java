package ru.vityaman.itmo.web.lab.taparia.logic.basic;

import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.AccessTokenService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.AuthService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.SecretsService;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.AuthenticationException;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;
import ru.vityaman.itmo.web.lab.taparia.storage.UserAccountStorage;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.user.UserCredentials;

public final class BasicAuthService implements AuthService {
    private final UserAccountStorage userAccountStorage;
    private final SecretsService secretsService;
    private final AccessTokenService tokenService;

    public BasicAuthService(
        UserAccountStorage userAccountStorage,
        SecretsService secretsService,
        AccessTokenService tokenService
    ) {
        this.userAccountStorage = userAccountStorage;
        this.secretsService = secretsService;
        this.tokenService = tokenService;
    }

    @Override
    public UserAccessToken register(UserCredentials credentials)
        throws LogicException {
        final var hashedPassword =
            secretsService.hashPassword(credentials.password());
        final var userDraft = UserAccount.of(
            UserAccount.Id.dummy(),
            credentials.username(),
            hashedPassword
        );
        try {
            final var user = userAccountStorage.insert(userDraft);
            return tokenService.computeAccessTokenIfAbsent(user.id());
        } catch (StorageException e) {
            throw new LogicException(
                "Can't register user with %s, try other name",
                e, credentials.username()
            );
        }
    }

    @Override
    public UserAccessToken login(UserCredentials credentials)
        throws LogicException {
        try {
            final var user = userAccountStorage
                .findUserByName(credentials.username())
                .orElseThrow(() -> new AuthenticationException(
                    "User with name %s not found",
                    credentials.username()
                ));
            boolean passwordsAreEqual =
                secretsService.testPasswords(
                    credentials.password(),
                    user.password()
                );
            if (!passwordsAreEqual) {
                throw new AuthenticationException("Passwords do not match");
            }
            return tokenService.computeAccessTokenIfAbsent(user.id());
        } catch (StorageException e) {
            throw new AuthenticationException(
                "Can't get information about %s", e, credentials.username()
            );
        }
    }

    @Override
    public void authenticate(UserAccessToken accessToken)
        throws AuthenticationException {
        boolean tokenNotFound = true;
        try {
            tokenNotFound = !tokenService.contains(accessToken);
        } catch (LogicException e) {
            throw new AuthenticationException("Can't check access token", e);
        }
        if (tokenNotFound) {
            throw new AuthenticationException(
                "Access token not found, "
                + "maybe it was expired or user does not "
                + "exists"
            );
        }
    }
}
