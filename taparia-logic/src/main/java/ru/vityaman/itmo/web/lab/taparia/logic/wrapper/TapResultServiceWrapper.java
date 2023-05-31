package ru.vityaman.itmo.web.lab.taparia.logic.wrapper;

import java.util.List;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.TapResult;
import ru.vityaman.itmo.web.lab.taparia.TapResult.Draft;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.TapResultService;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount.Id;

public class TapResultServiceWrapper implements TapResultService {
    private final TapResultService origin;

    protected TapResultServiceWrapper(TapResultService origin) {
        this.origin = origin;
    }

    @Override
    public final TapResult tap(Draft draft) throws LogicException {
        return origin.tap(draft);
    }

    @Override
    public final List<TapResult> findAllTapResults(
        Id ownerId, Picture.Id pictureId
    ) throws LogicException {
        return origin.findAllTapResults(ownerId, pictureId);
    }
}
