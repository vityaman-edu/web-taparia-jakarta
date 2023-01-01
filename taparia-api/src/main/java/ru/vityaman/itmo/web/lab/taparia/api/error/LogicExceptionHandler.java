package ru.vityaman.itmo.web.lab.taparia.api.error;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;

@Provider
public class LogicExceptionHandler
    implements ExceptionMapper<LogicException> {

    @Override
    public Response toResponse(LogicException e) {
        return Response
            .status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity(ErrorResponseBody.builder()
                .code(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .message(e.getMessage())
                .build())
            .build();
    }
}
