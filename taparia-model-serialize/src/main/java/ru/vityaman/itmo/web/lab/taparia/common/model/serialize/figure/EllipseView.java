package ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.figure.trivial.Ellipse;

@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class EllipseView extends FigureView {
    @Valid
    @JsonProperty(value = "center", required = true)
    VectorView center;

    @Valid
    @JsonProperty(value = "radius", required = true)
    VectorView radius;

    public EllipseView(VectorView center, VectorView radius) {
        super(FigureType.ELLIPSE);
        this.center = center;
        this.radius = radius;
    }

    public static EllipseView fromModel(Ellipse model) {
        return new EllipseView(
            VectorView.fromModel(model.center()),
            new VectorView(model.radius().x(), model.radius().y())
        );
    }

    @Override
    public Figure model() {
        return new Ellipse(
            center.model(),
            new Ellipse.Radius(
                radius.getX(), radius.getY()
            )
        );
    }
}
