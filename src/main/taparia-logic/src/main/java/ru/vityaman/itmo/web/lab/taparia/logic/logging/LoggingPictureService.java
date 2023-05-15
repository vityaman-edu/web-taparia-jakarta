package ru.vityaman.itmo.web.lab.taparia.logic.logging;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.common.logging.Log;
import ru.vityaman.itmo.web.lab.taparia.common.logging.LogFactory;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.PictureService;
import ru.vityaman.itmo.web.lab.taparia.logic.wrapper.PictureServiceWrapper;

import java.util.Collection;
import java.util.Optional;

public final class LoggingPictureService extends PictureServiceWrapper {
    private final Log log;

    public LoggingPictureService(LogFactory log, PictureService origin) {
        super(origin);
        this.log = log.newNamedLog("PictureService");
    }

    @Override
    public Optional<Picture> getPictureById(Picture.Id id)
        throws LogicException {
        final var picture = super.getPictureById(id);
        log.info(
            "getPictureById(%s) = %s",
            id, picture.map(Picture::hashCode)
        );
        return picture;
    }

    @Override
    public Collection<Picture> getAllPicturesByOwnerId(UserAccount.Id owner)
        throws LogicException {
        final var pictures = super.getAllPicturesByOwnerId(owner);
        log.info(
            "getAllPicturesByOwnerId(%s) = %s",
            owner, pictures.stream().map(Picture::hashCode).toList()
        );
        return pictures;
    }

    @Override
    public Picture.Id savePicture(Picture picture)
        throws LogicException {
        final var id = super.savePicture(picture);
        log.info("savePicture(ownerId = %s) = %s", picture.ownerId(), id);
        return id;
    }
}
