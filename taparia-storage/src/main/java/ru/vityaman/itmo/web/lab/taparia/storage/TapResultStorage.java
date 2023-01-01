package ru.vityaman.itmo.web.lab.taparia.storage;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.TapResult;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.util.Collection;
import java.util.List;

public interface TapResultStorage {
    TapResult.Id insert(TapResult draft) throws StorageException;

    List<TapResult> findAllResultsOrderedByCreationTime(
        UserAccount.Id ownerId, Picture.Id pictureId
    ) throws StorageException;

    void removeAllById(Collection<TapResult.Id> ids) throws StorageException;
}
