package ru.vityaman.itmo.web.lab.taparia.common.monitoring.metric;

public interface Counter {
    void increment();

    @FunctionalInterface
    interface OnChangeListener {
        void onChange(long value, Counter counter);
    }
}
