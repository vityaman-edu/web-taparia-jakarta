package ru.vityaman.itmo.web.lab.taparia.common.monitoring.mbean;

import ru.vityaman.itmo.web.lab.taparia.common.monitoring.metric.Counter;

public interface CounterMXBean {
    long getValue();
    void increment();

    final class Instance implements CounterMXBean, Counter {
        private long value = 0;

        @Override
        public long getValue() {
            return value;
        }

        @Override
        public void increment() {
            value += 1;
        }
    }
}
