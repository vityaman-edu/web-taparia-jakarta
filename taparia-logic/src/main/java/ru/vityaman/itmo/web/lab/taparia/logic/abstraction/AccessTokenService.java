package ru.vityaman.itmo.web.lab.taparia.logic.abstraction;

import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

public interface AccessTokenService {
    UserAccessToken computeAccessTokenIfAbsent(UserAccount.Id userId)
        throws LogicException;
    boolean contains(UserAccessToken token) throws LogicException;
}
