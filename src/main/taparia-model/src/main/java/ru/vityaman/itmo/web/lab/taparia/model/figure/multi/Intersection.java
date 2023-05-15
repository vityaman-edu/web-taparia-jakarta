package ru.vityaman.itmo.web.lab.taparia.figure.multi;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.figure.primitive.Point;

import java.util.List;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Intersection extends MultiFigure {
    public Intersection(List<? extends Figure> children) {
        super(children);
    }

    @Override
    public boolean contains(Point point) {
        return children().stream()
            .allMatch(child -> child.contains(point));
    }
}
