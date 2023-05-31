package ru.vityaman.itmo.web.lab.taparia.common.monitoring;

import ru.vityaman.itmo.web.lab.taparia.common.monitoring.metric.Counter;

public interface MonitoringService {
    Counter counter(String name, Counter.OnChangeListener onChange);

    default Counter counter(String name) {
        return counter(name, (value, counter) -> {
            // Do nothing
        });
    }

    default MonitoringService of(String namespace) {
        return new NamespacedMonitoringService(namespace, this);
    }
}
