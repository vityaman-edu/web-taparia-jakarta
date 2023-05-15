package ru.vityaman.itmo.web.lab.taparia.api.error;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiErrorHandler
    implements ExceptionMapper<ApiError> {

    @Override
    public Response toResponse(ApiError e) {
        return e.toResponse();
    }
}

