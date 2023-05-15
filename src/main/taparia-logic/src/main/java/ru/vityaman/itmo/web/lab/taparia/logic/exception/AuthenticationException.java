package ru.vityaman.itmo.web.lab.taparia.logic.exception;

public class AuthenticationException extends LogicException {
    public AuthenticationException(
        String pattern, Throwable cause, Object... vars
    ) {
        super(pattern, cause, vars);
    }

    public AuthenticationException(String pattern, Object... vars) {
        super(String.format(pattern, vars));
    }
}
