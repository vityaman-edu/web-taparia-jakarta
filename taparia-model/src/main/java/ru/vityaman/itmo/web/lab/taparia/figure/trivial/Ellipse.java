package ru.vityaman.itmo.web.lab.taparia.figure.trivial;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.figure.primitive.Point;

import static java.lang.Math.pow;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Ellipse extends Figure {
    Point center;
    Radius radius;

    public Ellipse(Point center, Radius radius) {
        this.center = center;
        this.radius = radius;
    }

    public static Ellipse circle(Point center, long radius) {
        return new Ellipse(center, new Radius(radius, radius));
    }

    @Override
    public boolean contains(Point point) {
        long dx = point.x() - center.x();
        long dy = point.y() - center.y();
        return pow(dx, 2) / pow(radius.x(), 2)
            + pow(dy, 2) / pow(radius.y(), 2) <= 1;
    }

    @Value
    public static class Radius {
        long x;
        long y;
    }
}

