package ru.vityaman.itmo.web.lab.taparia.logic.wrapper;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.PictureService;

import java.util.Collection;
import java.util.Optional;

public abstract class PictureServiceWrapper implements PictureService {
    private final PictureService origin;

    protected PictureServiceWrapper(PictureService origin) {
        this.origin = origin;
    }

    /** Override and call super. */
    @Override
    public Optional<Picture> getPictureById(Picture.Id id)
        throws LogicException {
        return origin.getPictureById(id);
    }

    /** Override and call super. */
    @Override
    public Collection<Picture> getAllPicturesByOwnerId(UserAccount.Id owner)
        throws LogicException {
        return origin.getAllPicturesByOwnerId(owner);
    }

    /** Override and call super. */
    @Override
    public Picture.Id savePicture(Picture picture) throws LogicException {
        return origin.savePicture(picture);
    }
}
