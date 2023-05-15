package ru.vityaman.itmo.web.lab.taparia.api.error;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionHandler
    implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException e) {
        return new ApiError(
            Response.Status.INTERNAL_SERVER_ERROR,
            "Internal server error"
        ).toResponse();
    }
}
