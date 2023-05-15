package ru.vityaman.itmo.web.lab.taparia.storage;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.util.Collection;
import java.util.Optional;

public interface PictureStorage {
    Optional<Picture> findPictureById(Picture.Id id)
        throws StorageException;

    Collection<Picture> findAllPicturesByOwnerId(UserAccount.Id ownerId)
        throws StorageException;

    Picture.Id insert(Picture picture)
        throws StorageException;
}
