package ru.vityaman.itmo.web.lab.taparia.logic.basic;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.TapResult;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.PictureService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.TapResultService;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;
import ru.vityaman.itmo.web.lab.taparia.storage.TapResultStorage;

import java.util.List;

public final class BasicTapResultService
    implements TapResultService {
    private final PictureService pictureService;
    private final TapResultStorage storage;

    public BasicTapResultService(
        PictureService pictureService,
        TapResultStorage storage
    ) {
        this.pictureService = pictureService;
        this.storage = storage;
    }

    @Override
    public TapResult tap(TapResult.Draft draft) throws LogicException {
        final var picture = pictureService
            .getPictureById(draft.pictureId())
            .orElseThrow(() -> new LogicException(
                "Picture with id %s not found", draft.pictureId()
            ));
        final var status =
            (picture.data().figure().contains(draft.point()))
                ? TapResult.Status.HIT
                : TapResult.Status.MISS;
        final var tap = TapResult.of(
            TapResult.Id.dummy(),
            draft.ownerId(),
            draft.pictureId(),
            draft.point(),
            status
        );
        try {
            final var id = storage.insert(tap);
            return tap.withId(id);
        } catch (StorageException e) {
            throw new LogicException("Can't insert tap result", e);
        }
    }

    @Override
    public List<TapResult> findAllTapResults(
        UserAccount.Id ownerId, Picture.Id pictureId
    ) throws LogicException {
        try {
            return storage
                .findAllResultsOrderedByCreationTime(ownerId, pictureId);
        } catch (StorageException e) {
            throw new LogicException("can't retrieve taps", e);
        }
    }
}
