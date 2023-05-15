package ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.taparia.figure.primitive.Point;

@Jacksonized
@Value
@SuperBuilder
@RequiredArgsConstructor
public class VectorView {
    @JsonProperty(value = "x", required = true)
    long x;

    @JsonProperty(value = "y", required = true)
    long y;

    public static VectorView fromModel(Point model) {
        return new VectorView(model.x(), model.y());
    }

    public Point model() {
        return Point.of(x, y);
    }
}
