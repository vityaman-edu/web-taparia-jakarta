package ru.vityaman.itmo.web.lab.taparia.common.monitoring.mbean;

import ru.vityaman.itmo.web.lab.taparia.common.monitoring.metric.Counter;

import javax.management.MXBean;

@MXBean
interface CounterMXBean extends Counter, MBean {
    long getValue();

    final class Instance implements CounterMXBean {
        private long value;

        Instance(long value) {
            this.value = value;
        }

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
