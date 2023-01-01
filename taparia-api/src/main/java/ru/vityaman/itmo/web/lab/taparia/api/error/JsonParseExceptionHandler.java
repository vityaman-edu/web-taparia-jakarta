package ru.vityaman.itmo.web.lab.taparia.api.error;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class JsonParseExceptionHandler
    implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(JsonParseException e) {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .header("Content-Type", "application/json")
            .entity(ErrorResponseBody.builder()
                .code(Response.Status.BAD_REQUEST.getStatusCode())
                .message(
                    e.getOriginalMessage()
                        + ", got "
                        + e.getRequestPayloadAsString())
                .build())
            .build();
    }
}
