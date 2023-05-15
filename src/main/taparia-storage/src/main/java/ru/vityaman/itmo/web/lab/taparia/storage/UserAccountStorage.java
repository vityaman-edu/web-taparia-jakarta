package ru.vityaman.itmo.web.lab.taparia.storage;

import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.util.Optional;

public interface UserAccountStorage {
    Optional<UserAccount> findUserById(UserAccount.Id id)
        throws StorageException;

    Optional<UserAccount> findUserByName(UserAccount.Name name)
        throws StorageException;

    UserAccount insert(UserAccount userDraft)
        throws StorageException;
}
