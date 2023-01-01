package ru.vityaman.itmo.web.lab.taparia.api.route.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import ru.vityaman.itmo.web.lab.taparia.api.error.NotFoundError;
import ru.vityaman.itmo.web.lab.taparia.api.route.auth.message.AccessTokenView;
import ru.vityaman.itmo.web.lab.taparia.api.route.user.message.UserView;
import ru.vityaman.itmo.web.lab.taparia.backend.Backend;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.user.UserCredentials;

@Path("/users")
public class UserRoute {
    private final Backend backend = Backend.instance();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public AccessTokenView register(
        @NotNull
        @Size(min = 4, max = 16)
        @HeaderParam("X-Auth-Username")
        String username,

        @NotNull
        @Size(min = 4, max = 32)
        @HeaderParam("X-Auth-Password")
        String password
    ) throws LogicException {
        final var credentials =
            UserCredentials.from(username, password);
        final var token = backend
            .userService()
            .registerUser(credentials);
        return AccessTokenView.builder()
            .userId(token.userId().value())
            .secret(token.secret().value())
            .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserView getByName(
        @NotNull
        @Size(min = 4, max = 16)
        @QueryParam("name")
        String username
    ) throws LogicException, NotFoundError {
        final var user = backend
            .userService()
            .findUserByName(UserAccount.Name.of(username))
            .orElseThrow(() -> new NotFoundError("user not found"));
        return UserView.builder()
            .id(user.id().value())
            .name(user.name().value())
            .build();
    }
}
