package ru.vityaman.itmo.web.lab.taparia.logic.abstraction;

import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.user.UserCredentials;

public interface SecretsService {
    UserAccount.Password hashPassword(UserCredentials.Password password);

    boolean testPasswords(
        UserCredentials.Password given,
        UserAccount.Password actual
    );

    UserAccessToken generateAccessToken(UserAccount.Id userId);
}
