package ru.vityaman.itmo.web.lab.taparia.storage;

import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.util.Optional;

public interface AccessTokenStorage {
    Optional<UserAccessToken> findTokenByUserId(UserAccount.Id id)
        throws StorageException;

    void upsertToken(UserAccessToken token)
        throws StorageException;
}
