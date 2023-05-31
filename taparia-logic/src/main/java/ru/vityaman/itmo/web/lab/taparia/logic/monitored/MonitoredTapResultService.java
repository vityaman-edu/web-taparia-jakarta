package ru.vityaman.itmo.web.lab.taparia.logic.monitored;

import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.TapResultService;
import ru.vityaman.itmo.web.lab.taparia.logic.wrapper.TapResultServiceWrapper;

public final class MonitoredTapResultService extends TapResultServiceWrapper {
    public MonitoredTapResultService(
        TapResultService origin
    ) {
        super(origin);
    }
}
