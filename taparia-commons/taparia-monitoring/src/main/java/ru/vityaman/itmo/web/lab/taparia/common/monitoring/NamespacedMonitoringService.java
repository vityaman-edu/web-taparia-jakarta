package ru.vityaman.itmo.web.lab.taparia.common.monitoring;

import ru.vityaman.itmo.web.lab.taparia.common.monitoring.metric.Counter;

public final class NamespacedMonitoringService implements MonitoringService {
    public static final String DELIMITER = ".";
    private final String namespace;
    private final MonitoringService origin;

    public NamespacedMonitoringService(
        String namespace,
        MonitoringService origin
    ) {
        this.namespace = namespace;
        this.origin = origin;
    }

    @Override
    public Counter counter(String name) {
        return origin.counter(namespaced(name));
    }

    private String namespaced(String name) {
        return namespace + DELIMITER + name;
    }
}
