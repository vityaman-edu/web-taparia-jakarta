package ru.vityaman.itmo.web.lab.taparia.figure.solo;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;

@Value
@NonFinal
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class SoloFigure extends Figure {
    Figure child;

    protected SoloFigure(Figure child) {
        this.child = child;
    }
}
