package ru.vityaman.itmo.web.lab.taparia.common.logging.jboss;

import org.jboss.logging.Logger;
import ru.vityaman.itmo.web.lab.taparia.common.logging.Log;

final class JbossLog implements Log {
    private final Logger logger;

    JbossLog(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void debug(String pattern, Object... vars) {
        logger.debug(String.format(pattern, vars));
    }

    @Override
    public void info(String pattern, Object... vars) {
        logger.info(String.format(pattern, vars));
    }

    @Override
    public void warn(String pattern, Object... vars) {
        logger.warn(String.format(pattern, vars));
    }

    @Override
    public void warn(String message, Throwable e) {
        logger.warn(message, e);
    }

    @Override
    public void error(String pattern, Object... vars) {
        logger.error(String.format(pattern, vars));
    }

    @Override
    public void error(String message, Throwable e) {
        logger.error(message, e);
    }
}
