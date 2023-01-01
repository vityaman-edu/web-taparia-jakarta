package ru.vityaman.itmo.web.lab.taparia.api.route.taps;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.TapResult;
import ru.vityaman.itmo.web.lab.taparia.api.route.taps.message.TapResultView;
import ru.vityaman.itmo.web.lab.taparia.backend.Backend;
import ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure.VectorView;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.util.List;

@Path("/pictures/{pictureId}/taps")
public class TapsRoute {
    private final Backend backend = Backend.instance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TapResultView createTap(
        @Positive
        @HeaderParam("X-Auth-User-ID")
        long userId,

        @NotNull
        @Size(min = 4, max = 32)
        @HeaderParam("X-Auth-Access-Token")
        String accessToken,

        @Positive
        @PathParam("pictureId")
        long pictureId,

        @NotNull
        @Valid
        VectorView point
    ) throws LogicException {
        final var token = UserAccessToken.from(userId, accessToken);
        backend.authService().authenticate(token);
        final var draft =
            TapResult.Draft.of(
                token.userId(),
                Picture.Id.of(pictureId),
                point.model()
            );
        final var result = backend
            .tapResultService()
            .tap(draft);
        return TapResultView.fromModel(result);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TapResultView> getTapResults(
        @Positive
        @HeaderParam("X-Auth-User-ID")
        long userId,

        @NotNull
        @Size(min = 4, max = 32)
        @HeaderParam("X-Auth-Access-Token")
        String accessToken,

        @Positive
        @PathParam("pictureId")
        long pictureId,

        @NotNull
        @Positive
        @QueryParam("owner_id")
        long ownerId
    ) throws LogicException {
        final var token = UserAccessToken.from(userId, accessToken);
        backend.authService().authenticate(token);
        return backend
            .tapResultService()
            .findAllTapResults(
                UserAccount.Id.of(ownerId),
                Picture.Id.of(pictureId))
            .stream()
            .map(TapResultView::fromModel)
            .toList();
    }
}
