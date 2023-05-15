package ru.vityaman.itmo.web.lab.taparia.logic.basic;

import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.AccessTokenService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.SecretsService;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.storage.AccessTokenStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.time.Clock;
import java.time.LocalDateTime;

public final class BasicAccessTokenService implements AccessTokenService {
    private final Clock clock;
    private final SecretsService secretsService;
    private final AccessTokenStorage accessTokenStorage;

    public BasicAccessTokenService(
        SecretsService secretsService,
        AccessTokenStorage accessTokenStorage,
        Clock clock
    ) {
        this.secretsService = secretsService;
        this.accessTokenStorage = accessTokenStorage;
        this.clock = clock;
    }

    @Override
    public UserAccessToken computeAccessTokenIfAbsent(UserAccount.Id userId)
        throws LogicException {
        try {
            final var mbToken = accessTokenStorage.findTokenByUserId(userId);
            if (mbToken.isEmpty()) {
                return generateUpsertAndGetToken(userId);
            }
            final var token = mbToken.get();
            if (token.willBeExpiredAt(now())) {
                return generateUpsertAndGetToken(userId);
            }
            return token;
        } catch (StorageException e) {
            throw new LogicException("Can't get access token", e);
        }
    }

    @Override
    public boolean contains(UserAccessToken token) throws LogicException {
        try {
            final var mbToken = accessTokenStorage
                .findTokenByUserId(token.userId());
            if (mbToken.isEmpty()) {
                return false;
            }
            final var tk = mbToken.get();
            if (tk.willBeExpiredAt(now())) {
                return false;
            }
            return tk.userId().equals(token.userId())
                && tk.secret().equals(token.secret());
        } catch (StorageException e) {
            throw new LogicException("Can't get access token", e);
        }
    }

    private UserAccessToken generateUpsertAndGetToken(UserAccount.Id userId)
        throws StorageException {
        final var newToken = secretsService.generateAccessToken(userId);
        accessTokenStorage.upsertToken(newToken);
        return newToken;
    }

    private LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
}
