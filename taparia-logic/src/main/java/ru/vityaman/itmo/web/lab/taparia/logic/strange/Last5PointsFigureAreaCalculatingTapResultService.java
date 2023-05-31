package ru.vityaman.itmo.web.lab.taparia.logic.strange;

import ru.vityaman.itmo.web.lab.taparia.TapResult;
import ru.vityaman.itmo.web.lab.taparia.figure.primitive.Point;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.TapResultService;
import ru.vityaman.itmo.web.lab.taparia.logic.exception.LogicException;
import ru.vityaman.itmo.web.lab.taparia.logic.wrapper.TapResultServiceWrapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.DoubleConsumer;

public final class Last5PointsFigureAreaCalculatingTapResultService
        extends TapResultServiceWrapper {

    private static final int LAST_POINTS_SIZE_LIMIT = 5;
    private final LinkedList<Point> lastPoints;
    private final DoubleConsumer squareConsumer;

    public Last5PointsFigureAreaCalculatingTapResultService(
            TapResultService origin,
            DoubleConsumer squareConsumer
    ) {
        super(origin);
        this.lastPoints = new LinkedList<>();
        this.squareConsumer = squareConsumer;
    }

    @Override
    public TapResult tap(TapResult.Draft draft)
            throws LogicException {
        final var tap = super.tap(draft);
        synchronized (lastPoints) {
            lastPoints.addLast(tap.point());
            if (lastPoints.size() > LAST_POINTS_SIZE_LIMIT) {
                lastPoints.removeFirst();
                squareConsumer.accept(polygonArea(lastPoints));
            }
        }
        return tap;
    }

    // geeksforgeeks.org/area-of-a-polygon-with-given-n-ordered-vertices/
    private double polygonArea(List<Point> points) {
        final var p = new ArrayList<>(points);
        double area = 0.0;
        int j = p.size() - 1;
        for (int i = 0; i < p.size(); i++) {
            area += (points.get(j).x() + points.get(i).x())
                    * (points.get(j).y() - points.get(i).y());
            j = i;
        }
        return Math.abs(area / 2.0);
    }
}
