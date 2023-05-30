package ru.vityaman.itmo.web.lab.taparia.api.error;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionHandler
    implements ExceptionMapper<IllegalArgumentException> {

    // TODO: security hole
    @Override
    public Response toResponse(IllegalArgumentException e) {
        return new ApiError(
            Response.Status.BAD_REQUEST,
            e.getMessage()
        ).toResponse();
    }
}
