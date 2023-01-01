package ru.vityaman.itmo.web.lab.taparia.logic.abstraction;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.util.Collection;
import java.util.Optional;

public interface PictureService {
    Optional<Picture> getPictureById(Picture.Id id)
        throws LogicException;

    Collection<Picture> getAllPicturesByOwnerId(UserAccount.Id owner)
        throws LogicException;

    Picture.Id savePicture(Picture picture)
        throws LogicException;
}
