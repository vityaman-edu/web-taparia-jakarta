package ru.vityaman.itmo.web.lab.taparia.common.monitoring.mbean;

interface MBeanServer {
    void register(String name, MBean bean);
}
