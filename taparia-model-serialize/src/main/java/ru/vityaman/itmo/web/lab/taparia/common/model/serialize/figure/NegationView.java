package ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.figure.solo.Negation;

@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class NegationView extends FigureView {
    @Valid
    @JsonProperty(value = "child", required = true)
    FigureView child;

    public NegationView(FigureView child) {
        super(FigureType.NEGATION);
        this.child = child;
    }

    static FigureView fromModel(Negation model) {
        return new NegationView(
            FigureView.fromModelFigure(model.child())
        );
    }

    @Override
    public Figure model() {
        return new Negation(child.model());
    }
}
