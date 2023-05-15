package ru.vityaman.itmo.web.lab.taparia.logic.logging;

import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.user.UserCredentials;
import ru.vityaman.itmo.web.lab.taparia.common.logging.Log;
import ru.vityaman.itmo.web.lab.taparia.common.logging.LogFactory;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.UserService;
import ru.vityaman.itmo.web.lab.taparia.logic.wrapper.UserServiceWrapper;

import java.util.Optional;

public final class LoggingUserService extends UserServiceWrapper {
    private final Log log;

    public LoggingUserService(LogFactory log, UserService origin) {
        super(origin);
        this.log = log.newNamedLog("UserService");
    }

    @Override
    public UserAccessToken registerUser(UserCredentials credentials)
        throws LogicException {
        final var token = super.registerUser(credentials);
        log.info(
            "registerUser(%s) = %s",
            credentials.username().hashCode(),
            token.userId()
        );
        return token;
    }

    @Override
    public Optional<UserAccount> findUserByName(UserAccount.Name name)
        throws LogicException {
        final var account = super.findUserByName(name);
        log.info(
            "findUserByName(%s) = %s",
            name.hashCode(),
            account.map(UserAccount::id)
        );
        return account;
    }
}
