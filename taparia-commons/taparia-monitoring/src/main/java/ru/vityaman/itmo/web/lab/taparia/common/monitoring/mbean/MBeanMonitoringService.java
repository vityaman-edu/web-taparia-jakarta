package ru.vityaman.itmo.web.lab.taparia.common.monitoring.mbean;

import ru.vityaman.itmo.web.lab.taparia.common.monitoring.MonitoringService;
import ru.vityaman.itmo.web.lab.taparia.common.monitoring.metric.Counter;

public final class MBeanMonitoringService implements MonitoringService {
    private final MBeanServer server;

    public MBeanMonitoringService(MBeanServer server) {
        this.server = server;
    }

    @Override
    public Counter counter(String name) {
        var counter = new CounterMXBean.Instance(0);
        server.register(name, counter);
        return counter;
    }
}
