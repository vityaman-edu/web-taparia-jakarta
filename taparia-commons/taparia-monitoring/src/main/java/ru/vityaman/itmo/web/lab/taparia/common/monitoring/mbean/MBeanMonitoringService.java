package ru.vityaman.itmo.web.lab.taparia.common.monitoring.mbean;

import lombok.SneakyThrows;
import ru.vityaman.itmo.web.lab.taparia.common.monitoring.MonitoringService;
import ru.vityaman.itmo.web.lab.taparia.common.monitoring.metric.Counter;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public final class MBeanMonitoringService implements MonitoringService {
    private final MBeanServer server;

    public MBeanMonitoringService(MBeanServer server) {
        this.server = server;
    }

    @SneakyThrows
    @Override
    public Counter counter(String name) {
        final var counter = new CounterMXBean.Instance();
        register(name, counter);
        return counter;
    }

    @SneakyThrows
    private void register(String name, Object mbean) {
        server.registerMBean(mbean, new ObjectName(String.format(
            "%s:type=%s,name=%s",
            mbean.getClass().getPackageName(),
            mbean.getClass().getCanonicalName(),
            name
        )));
    }
}
