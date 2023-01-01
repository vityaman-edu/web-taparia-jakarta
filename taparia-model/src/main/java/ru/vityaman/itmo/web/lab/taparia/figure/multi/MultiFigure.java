package ru.vityaman.itmo.web.lab.taparia.figure.multi;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;

import java.util.List;

@Value
@NonFinal
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class MultiFigure extends Figure {
    List<? extends Figure> children;

    MultiFigure(List<? extends Figure> children) {
        this.children = children;
    }

    public final List<? extends Figure> children() {
        return children;
    }
}
