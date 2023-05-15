package ru.vityaman.itmo.web.lab.taparia.logic.secured;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.AuthService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.PictureService;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.AuthenticationException;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.util.Collection;
import java.util.Optional;

public final class SecuredPictureService implements PictureService {
    private final AuthService authService;
    private final PictureService origin;
    private final UserAccessToken token;

    public SecuredPictureService(
        AuthService authService,
        PictureService origin,
        UserAccessToken token
    ) {
        this.authService = authService;
        this.origin = origin;
        this.token = token;
    }

    @Override
    public Optional<Picture> getPictureById(Picture.Id id)
        throws LogicException {
        authService.authenticate(token);
        final var maybePicture = origin.getPictureById(id);
        if (maybePicture.isEmpty()) {
            return Optional.empty();
        }
        final var picture = maybePicture.get();
        if (!token.userId().equals(picture.ownerId())) {
            throw new AuthenticationException(
                "You have no permission to view this picture"
            );
        }
        return maybePicture;
    }

    @Override
    public Collection<Picture> getAllPicturesByOwnerId(UserAccount.Id ownerId)
        throws LogicException {
        authService.authenticate(token);
        if (!token.userId().equals(ownerId)) {
            throw new AuthenticationException(
                "You have no permission to view these pictures"
            );
        }
        return origin.getAllPicturesByOwnerId(ownerId);
    }

    @Override
    public Picture.Id savePicture(Picture picture)
        throws LogicException {
        return origin.savePicture(picture);
    }
}
