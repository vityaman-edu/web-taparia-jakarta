package ru.vityaman.itmo.web.lab.taparia.logic.basic;

import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.user.UserCredentials;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.AuthService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.UserService;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;
import ru.vityaman.itmo.web.lab.taparia.storage.UserAccountStorage;

import java.util.Optional;

public final class BasicUserService implements UserService {
    private final AuthService authService;
    private final UserAccountStorage storage;

    public BasicUserService(
        UserAccountStorage storage,
        AuthService authService
    ) {
        this.storage = storage;
        this.authService = authService;
    }

    @Override
    public UserAccessToken registerUser(UserCredentials credentials)
        throws LogicException {
        return authService.register(credentials);
    }

    @Override
    public Optional<UserAccount> findUserByName(UserAccount.Name name)
        throws LogicException {
        try {
            return storage.findUserByName(name);
        } catch (StorageException e) {
            throw new LogicException("There is no user with name %s", e, name);
        }
    }
}
