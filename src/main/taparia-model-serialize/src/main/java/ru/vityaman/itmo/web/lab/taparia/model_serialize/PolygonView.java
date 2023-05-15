package ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.figure.trivial.Polygon;

import java.util.List;

@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class PolygonView extends FigureView {
    @NotEmpty
    @JsonProperty(value = "points", required = true)
    List<@Valid VectorView> points;

    public PolygonView(List<VectorView> points) {
        super(FigureType.POLYGON);
        this.points = points;
    }

    public static FigureView fromModel(Polygon model) {
        return new PolygonView(
            model.points().stream().map(VectorView::fromModel).toList()
        );
    }

    @Override
    public Figure model() {
        return new Polygon(
            points.stream().map(VectorView::model).toList()
        );
    }
}
