package ru.vityaman.itmo.web.lab.taparia.logic.basic;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.PictureService;
import ru.vityaman.itmo.web.lab.taparia.storage.PictureStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;

import java.util.Collection;
import java.util.Optional;

public final class BasicPictureService implements PictureService {
    private final PictureStorage storage;

    public BasicPictureService(PictureStorage storage) {
        this.storage = storage;
    }

    @Override
    public Optional<Picture> getPictureById(Picture.Id id)
        throws LogicException {
        try {
            return storage.findPictureById(id);
        } catch (StorageException e) {
            throw new LogicException(
                "Can't find picture %s in %s collection", e, id.value()
            );
        }
    }

    @Override
    public Collection<Picture> getAllPicturesByOwnerId(UserAccount.Id owner)
        throws LogicException {
        try {
            return storage.findAllPicturesByOwnerId(owner);
        } catch (StorageException e) {
            throw new LogicException(
                "Can't get pictures of %s", e, owner.value()
            );
        }
    }

    @Override
    public Picture.Id savePicture(Picture picture) throws LogicException {
        try {
            return storage.insert(picture);
        } catch (StorageException e) {
            throw new LogicException(
                "Can't save picture with name %s and owner %s, "
                    + "maybe it already exists",
                e,
                picture.name(), picture.ownerId()
            );
        }
    }
}
