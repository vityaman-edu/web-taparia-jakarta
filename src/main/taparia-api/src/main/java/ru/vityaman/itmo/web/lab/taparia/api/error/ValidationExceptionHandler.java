package ru.vityaman.itmo.web.lab.taparia.api.error;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Provider
public class ValidationExceptionHandler
    implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(
                ResponseEntity.builder()
                    .code(Response.Status.BAD_REQUEST.getStatusCode())
                    .message("Invalid request data")
                    .violations(
                        e.getConstraintViolations().stream()
                            .map(v -> String.format(
                                "value %s is invalid as %s",
                                v.getInvalidValue(),
                                v.getMessage()
                            )).toList())
                    .build())
            .build();
    }

    @Jacksonized
    @SuperBuilder
    @Value
    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static class ResponseEntity extends ErrorResponseBody {
        int code;
        List<String> violations;
    }
}
