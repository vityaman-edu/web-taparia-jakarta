package ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.taparia.figure.solo.Colored;

@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ColoredView extends FigureView {
    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3,4}){1,2}$")
    @JsonProperty(value = "color", required = true)
    String color;

    @Valid
    @JsonProperty(value = "child", required = true)
    FigureView child;

    public ColoredView(String color, FigureView child) {
        super("colored");
        this.color = color;
        this.child = child;
    }

    public static ColoredView fromModel(Colored model) {
        return new ColoredView(
            model.color(),
            FigureView.fromModelFigure(model.child())
        );
    }

    public Colored model() {
        return new Colored(color, child.model());
    }
}
