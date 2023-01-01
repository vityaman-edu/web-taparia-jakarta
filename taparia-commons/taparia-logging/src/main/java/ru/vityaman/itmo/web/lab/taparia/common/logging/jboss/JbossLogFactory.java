package ru.vityaman.itmo.web.lab.taparia.common.logging.jboss;

import org.jboss.logging.Logger;
import ru.vityaman.itmo.web.lab.taparia.common.logging.Log;
import ru.vityaman.itmo.web.lab.taparia.common.logging.LogFactory;

public final class JbossLogFactory implements LogFactory {
    @Override
    public Log newNamedLog(String name) {
        return new JbossLog(Logger.getLogger(name));
    }
}
