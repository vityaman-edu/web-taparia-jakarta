package ru.vityaman.itmo.web.lab.taparia.api.error;


import jakarta.ws.rs.core.Response;

public class ApiError extends Exception {
    private final Response.Status code;

    public ApiError(Response.Status code, String message) {
        super(message);
        this.code = code;
    }

    public final Response.Status status() {
        return code;
    }

    public final Response toResponse() {
        return Response
            .status(status())
            .entity(ErrorResponseBody.builder()
                .code(status().getStatusCode())
                .message(getMessage())
                .build())
            .build();
    }
}
