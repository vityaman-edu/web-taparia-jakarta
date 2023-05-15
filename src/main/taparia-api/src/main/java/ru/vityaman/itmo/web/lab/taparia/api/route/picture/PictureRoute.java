package ru.vityaman.itmo.web.lab.taparia.api.route.picture;

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
import ru.vityaman.itmo.web.lab.taparia.api.route.picture.message.CreatePictureRequest;
import ru.vityaman.itmo.web.lab.taparia.api.route.picture.message.CreatePictureResponse;
import ru.vityaman.itmo.web.lab.taparia.api.route.picture.message.PictureView;
import ru.vityaman.itmo.web.lab.taparia.backend.Backend;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.util.List;

@Path("/pictures")
public class PictureRoute {
    private final Backend backend = Backend.instance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreatePictureResponse create(
        @Positive
        @HeaderParam("X-Auth-User-ID")
        long userId,

        @NotNull
        @Size(min = 4, max = 32)
        @HeaderParam("X-Auth-Access-Token")
        String accessToken,

        @NotNull
        @Valid
        CreatePictureRequest request
    ) throws LogicException {
        final var token = UserAccessToken.from(userId, accessToken);
        final var pictureService = backend.pictureService(token);
        final var picture = Picture.of(
            Picture.Id.dummy(),
            token.userId(),
            Picture.Name.of(request.getName()),
            Picture.Data.of(request.getData().model())
        );
        final var pictureId = pictureService.savePicture(picture);
        return CreatePictureResponse.builder()
            .pictureId(pictureId.value())
            .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PictureView> getPicturesByOwnerId(
        @Positive
        @HeaderParam("X-Auth-User-ID")
        long userId,

        @NotNull
        @Size(min = 4, max = 32)
        @HeaderParam("X-Auth-Access-Token")
        String accessToken,

        @NotNull
        @QueryParam("owner_id")
        long ownerId
    ) throws LogicException {
        final var token = UserAccessToken.from(userId, accessToken);
        return backend
            .pictureService(token)
            .getAllPicturesByOwnerId(UserAccount.Id.of(ownerId))
            .stream()
            .map(PictureView::fromModel)
            .toList();
    }

    @GET
    @Path("{pictureId}")
    @Produces(MediaType.APPLICATION_JSON)
    public PictureView getById(
        @Positive
        @HeaderParam("X-Auth-User-ID")
        long userId,

        @NotNull
        @Size(min = 4, max = 32)
        @HeaderParam("X-Auth-Access-Token")
        String accessToken,

        @NotNull
        @PathParam("pictureId")
        long pictureId
    ) throws LogicException {
        final var token = UserAccessToken.from(userId, accessToken);
        final var picture = backend
            .pictureService(token)
            .getPictureById(Picture.Id.of(pictureId))
            .orElseThrow(() -> new LogicException("Picture not found"));
        return PictureView.fromModel(picture);
    }
}
