package ru.vityaman.itmo.web.lab.taparia.figure.trivial;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.figure.primitive.Point;
import ru.vityaman.itmo.web.lab.taparia.figure.primitive.Segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Polygon extends Figure {
    private static final long FAIREST_POINT = 999L;
    private static final long SHIFT = 10L;

    List<Point> points;

    public Polygon(List<Point> points) {
        this.points = points;
    }

    public static Polygon rectangle(Point leftTop, Point rightBottom) {
        return new Polygon(List.of(
            leftTop,
            Point.of(rightBottom.x(), leftTop.y()),
            rightBottom,
            Point.of(leftTop.x(), rightBottom.y())
        ));
    }

    @Override
    public boolean contains(Point point) {
        long fairestPoint = points.stream()
            .flatMap(p -> Stream.of(p.x(), p.y()))
            .max(Comparator.comparingLong(Math::abs))
            .orElse(FAIREST_POINT);
        Point corner = Point.of(fairestPoint, fairestPoint + SHIFT);
        Segment ray = new Segment(point, corner);
        return sides().stream()
            .filter(side -> side.intersects(ray))
            .count() % 2 == 1;
    }

    private List<Segment> sides() {
        int pointsCount = points.size();
        List<Segment> result = new ArrayList<>(pointsCount);
        for (int i = 1; i <= pointsCount; i++) {
            result.add(new Segment(
                points.get(i - 1),
                points.get(i % pointsCount))
            );
        }
        return Collections.unmodifiableList(result);
    }
}

