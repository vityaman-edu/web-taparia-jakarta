package ru.vityaman.itmo.web.lab.taparia.figure.solo;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.figure.primitive.Point;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Negation extends SoloFigure {
    public Negation(Figure child) {
        super(child);
    }

    @Override
    public boolean contains(Point point) {
        return !child().contains(point);
    }
}
