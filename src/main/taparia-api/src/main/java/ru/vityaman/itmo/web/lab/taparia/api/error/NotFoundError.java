package ru.vityaman.itmo.web.lab.taparia.api.error;

import jakarta.ws.rs.core.Response;

public class NotFoundError extends ApiError {
    public NotFoundError(String message) {
        super(Response.Status.NOT_FOUND, message);
    }
}
