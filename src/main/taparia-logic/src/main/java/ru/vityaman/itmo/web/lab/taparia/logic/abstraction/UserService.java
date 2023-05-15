package ru.vityaman.itmo.web.lab.taparia.logic.abstraction;

import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.user.UserCredentials;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;

import java.util.Optional;

public interface UserService {
    UserAccessToken registerUser(UserCredentials credentials)
        throws LogicException;

    Optional<UserAccount> findUserByName(UserAccount.Name name)
        throws LogicException;
}
