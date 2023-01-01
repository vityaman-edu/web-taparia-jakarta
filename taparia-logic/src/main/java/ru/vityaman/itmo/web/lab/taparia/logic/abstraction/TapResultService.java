package ru.vityaman.itmo.web.lab.taparia.logic.abstraction;

import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.TapResult;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;

import java.util.List;

public interface TapResultService {
    TapResult tap(TapResult.Draft draft)
        throws LogicException;

    List<TapResult> findAllTapResults(
        UserAccount.Id ownerId, Picture.Id pictureId
    ) throws LogicException;
}
