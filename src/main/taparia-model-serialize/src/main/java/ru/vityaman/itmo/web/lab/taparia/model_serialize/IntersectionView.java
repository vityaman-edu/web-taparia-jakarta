package ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.figure.multi.Intersection;

import java.util.List;

@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IntersectionView extends AggregatorView {
    public IntersectionView(List<FigureView> children) {
        super(FigureType.INTERSECTION, children);
    }

    static IntersectionView fromModel(Intersection model) {
        return new IntersectionView(
            model.children().stream()
                .map(FigureView::fromModelFigure)
                .toList()
        );
    }

    @Override
    public Figure model() {
        return new Intersection(
            getChildren().stream().map(FigureView::model).toList()
        );
    }
}
