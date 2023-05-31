package ru.vityaman.itmo.web.lab.taparia.common.monitoring.mbean;

import ru.vityaman.itmo.web.lab.taparia.common.monitoring.metric.Counter;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationBroadcasterSupport;

public interface CounterMXBean {
    long getValue();

    void increment();

    final class Instance extends NotificationBroadcasterSupport
            implements CounterMXBean, Counter {
        private long value;
        private final OnChangeListener onChange;

        public Instance(long value, OnChangeListener onChange) {
            this.value = value;
            this.onChange = onChange;
        }

        @Override
        public synchronized long getValue() {
            return value;
        }

        @Override
        public synchronized void increment() {
            value += 1;
            onChange.onChange(value, this);
        }

        @Override
        public MBeanNotificationInfo[] getNotificationInfo() {
            String[] types = new String[] {
                    AttributeChangeNotification.ATTRIBUTE_CHANGE
            };
            String name = AttributeChangeNotification.class.getName();
            String description = "Counter was incremented";
            MBeanNotificationInfo info =
                    new MBeanNotificationInfo(types, name, description);
            return new MBeanNotificationInfo[] {info};
        }
    }
}
