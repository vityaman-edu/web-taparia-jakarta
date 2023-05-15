package ru.vityaman.itmo.web.lab.taparia.logic.exception;

public class LogicException extends Exception {
    public LogicException(String pattern, Throwable cause, Object... vars) {
        super(String.format(pattern, vars), cause);
    }

    public LogicException(String pattern, Object... vars) {
        super(String.format(pattern, vars));
    }
}
