package ru.vityaman.itmo.web.lab.taparia.logic.abstraction;

import ru.vityaman.itmo.web.lab.taparia.logic.exception.AuthenticationException;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserCredentials;

public interface AuthService {
    UserAccessToken register(UserCredentials credentials)
        throws LogicException;

    UserAccessToken login(UserCredentials credentials)
        throws LogicException;

    void authenticate(UserAccessToken accessToken)
        throws AuthenticationException;
}
