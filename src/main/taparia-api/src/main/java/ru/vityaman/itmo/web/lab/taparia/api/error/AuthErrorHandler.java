package ru.vityaman.itmo.web.lab.taparia.api.error;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.SneakyThrows;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.AuthenticationException;

@Provider
public class AuthErrorHandler
    implements ExceptionMapper<AuthenticationException> {

    @SneakyThrows
    @Override
    public Response toResponse(AuthenticationException e) {
        return new ApiError(Response.Status.UNAUTHORIZED, e.getMessage())
            .toResponse();
    }
}
