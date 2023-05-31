package ru.vityaman.itmo.web.lab.taparia.common.monitoring.mbean;

public final class NamespacedMBeanServer implements MBeanServer {
    private final String namespace;
    private final MBeanServer origin;

    public NamespacedMBeanServer(String namespace, MBeanServer origin) {
        this.namespace = namespace;
        this.origin = origin;
    }

    @Override
    public void register(String name, MBean bean) {
        origin.register(String.format("%s:%s", namespace, name), bean);
    }
}
