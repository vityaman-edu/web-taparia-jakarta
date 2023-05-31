package ru.vityaman.itmo.web.lab.taparia.logic.monitored;

import ru.vityaman.itmo.web.lab.taparia.TapResult;
import ru.vityaman.itmo.web.lab.taparia.common.monitoring.MonitoringService;
import ru.vityaman.itmo.web.lab.taparia.common.monitoring.mbean.CounterMXBean;
import ru.vityaman.itmo.web.lab.taparia.common.monitoring.metric.Counter;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.TapResultService;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.logic.wrapper.TapResultServiceWrapper;

import javax.management.AttributeChangeNotification;

public final class MonitoredTapResultService extends TapResultServiceWrapper {
    private final Counter tapsCountTotal;
    private final Counter tapsCountSuccess;

    public MonitoredTapResultService(
            TapResultService origin,
            MonitoringService monitoring
    ) {
        super(origin);
        tapsCountTotal = monitoring.counter(
                "taps.count.total", (count, counter) -> {
                    final var period = 5;
                    final var jxm = (CounterMXBean.Instance) counter;
                    if (jxm.getValue() % period == 0) {
                        jxm.sendNotification(new AttributeChangeNotification(
                                counter,
                                count,
                                System.currentTimeMillis(),
                                "Next 5 points passed",
                                "value",
                                "long",
                                count - 1,
                                count
                        ));
                    }
                });
        tapsCountSuccess = monitoring.counter("taps.count.success");
    }

    @Override
    public TapResult tap(TapResult.Draft draft) throws LogicException {
        var result = super.tap(draft);
        tapsCountTotal.increment();
        if (result.status().equals(TapResult.Status.HIT)) {
            tapsCountSuccess.increment();
        }
        return result;
    }
}
