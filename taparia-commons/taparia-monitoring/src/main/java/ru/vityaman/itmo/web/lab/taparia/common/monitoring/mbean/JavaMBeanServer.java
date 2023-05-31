package ru.vityaman.itmo.web.lab.taparia.common.monitoring.mbean;

import lombok.SneakyThrows;

import javax.management.ObjectName;

public final class JavaMBeanServer implements MBeanServer {
    private final javax.management.MBeanServer server;

    public JavaMBeanServer(javax.management.MBeanServer server) {
        this.server = server;
    }

    @SneakyThrows
    @Override
    public void register(String name, MBean bean) {
        server.registerMBean(bean, new ObjectName(name));
    }
}
