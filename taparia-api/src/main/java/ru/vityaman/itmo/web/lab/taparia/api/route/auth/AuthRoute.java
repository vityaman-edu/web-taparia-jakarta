package ru.vityaman.itmo.web.lab.taparia.api.route.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import ru.vityaman.itmo.web.lab.taparia.api.route.auth.message.AccessTokenView;
import ru.vityaman.itmo.web.lab.taparia.backend.Backend;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.user.UserCredentials;

@Path("/auth")
public class AuthRoute {
    private final Backend backend = Backend.instance();

    @GET
    @Path("/access_tokens")
    @Produces(MediaType.APPLICATION_JSON)
    public AccessTokenView login(
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
            .authService()
            .login(credentials);
        return AccessTokenView.builder()
            .userId(token.userId().value())
            .secret(token.secret().value())
            .build();
    }
}
