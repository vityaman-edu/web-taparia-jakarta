package ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.figure.multi.Intersection;
import ru.vityaman.itmo.web.lab.taparia.figure.multi.Union;
import ru.vityaman.itmo.web.lab.taparia.figure.solo.Colored;
import ru.vityaman.itmo.web.lab.taparia.figure.solo.Negation;
import ru.vityaman.itmo.web.lab.taparia.figure.trivial.Ellipse;
import ru.vityaman.itmo.web.lab.taparia.figure.trivial.Polygon;

@Value
@NonFinal
@SuperBuilder
@RequiredArgsConstructor
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(
        value = PolygonView.class,
        name = FigureType.POLYGON
    ),
    @JsonSubTypes.Type(
        value = EllipseView.class,
        name = FigureType.ELLIPSE
    ),
    @JsonSubTypes.Type(
        value = UnionView.class,
        name = FigureType.UNION
    ),
    @JsonSubTypes.Type(
        value = IntersectionView.class,
        name = FigureType.INTERSECTION
    ),
    @JsonSubTypes.Type(
        value = NegationView.class,
        name = FigureType.NEGATION
    ),
    @JsonSubTypes.Type(
        value = ColoredView.class,
        name = FigureType.COLORED
    )
})
public abstract class FigureView {
    @Pattern(regexp = FigureType.PATTERN)
    @JsonProperty(value = "type", required = true)
    String type;

    public abstract Figure model();

    public static FigureView fromModelFigure(Figure model) {
        Class<? extends Figure> klass = model.getClass();
        if (klass.equals(Ellipse.class)) {
            return EllipseView.fromModel((Ellipse) model);
        }
        if (klass.equals(Intersection.class)) {
            return IntersectionView.fromModel((Intersection) model);
        }
        if (klass.equals(Negation.class)) {
            return NegationView.fromModel((Negation) model);
        }
        if (klass.equals(Polygon.class)) {
            return PolygonView.fromModel((Polygon) model);
        }
        if (klass.equals(Union.class)) {
            return UnionView.fromModel((Union) model);
        }
        if (klass.equals(Colored.class)) {
            return ColoredView.fromModel((Colored) model);
        }
        throw new AssertionError(String.format(
            "Figure %s with type %s was not handled",
            model, klass
        ));
    }
}
