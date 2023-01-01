package ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure;

import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.figure.multi.Union;

import java.util.List;

@Jacksonized
@Value
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UnionView extends AggregatorView {
    public UnionView(List<FigureView> children) {
        super(FigureType.UNION, children);
    }

    static FigureView fromModel(Union model) {
        return new UnionView(
            model.children().stream()
                .map(FigureView::fromModelFigure)
                .toList()
        );
    }

    @Override
    public Figure model() {
        return new Union(
            getChildren().stream().map(FigureView::model).toList()
        );
    }
}
