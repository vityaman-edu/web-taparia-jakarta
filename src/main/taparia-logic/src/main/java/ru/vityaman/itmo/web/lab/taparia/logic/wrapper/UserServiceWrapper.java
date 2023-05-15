package ru.vityaman.itmo.web.lab.taparia.logic.wrapper;

import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.user.UserCredentials;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.UserService;

import java.util.Optional;

public abstract class UserServiceWrapper implements UserService {
    private final UserService origin;

    protected UserServiceWrapper(UserService origin) {
        this.origin = origin;
    }

    /**
     * Override and call super.
     */
    @Override
    public UserAccessToken registerUser(UserCredentials credentials)
        throws LogicException {
        return origin.registerUser(credentials);
    }

    /** Override and call super. */
    @Override
    public Optional<UserAccount> findUserByName(UserAccount.Name name)
        throws LogicException {
        return origin.findUserByName(name);
    }
}
