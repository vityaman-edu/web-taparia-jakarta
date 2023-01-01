package ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Value
@NonFinal
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public abstract class AggregatorView extends FigureView {
    @NotEmpty
    @JsonProperty(value = "children", required = true)
    List<@Valid FigureView> children;

    protected AggregatorView(String type, List<FigureView> children) {
        super(type);
        this.children = children;
    }
}
