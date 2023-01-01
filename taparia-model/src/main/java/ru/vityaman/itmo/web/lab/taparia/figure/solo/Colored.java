package ru.vityaman.itmo.web.lab.taparia.figure.solo;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.figure.primitive.Point;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Colored extends SoloFigure {
    String color;

    public Colored(String color, Figure child) {
        super(child);
        this.color = color;
    }

    @Override
    public boolean contains(Point point) {
        return child().contains(point);
    }
}
