package ru.vityaman.itmo.web.lab.taparia.common.logging;

public interface Log {
    void debug(String pattern, Object... vars);
    void info(String pattern, Object... vars);
    void warn(String pattern, Object... vars);
    void warn(String message, Throwable e);
    void error(String pattern, Object... vars);
    void error(String message, Throwable e);
}
