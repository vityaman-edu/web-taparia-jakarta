package ru.vityaman.itmo.web.lab.taparia.api.route.picture.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.taparia.Picture;
import ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure.FigureView;

@Jacksonized
@Value
@SuperBuilder
public class PictureView {
    @JsonProperty(value = "id", required = true)
    long id;

    @JsonProperty(value = "owner_id", required = true)
    long ownerId;

    @JsonProperty(value = "name", required = true)
    String name;

    @JsonProperty(value = "data", required = true)
    FigureView data;

    public static PictureView fromModel(Picture picture) {
        return PictureView.builder()
            .id(picture.id().value())
            .ownerId(picture.ownerId().value())
            .name(picture.name().value())
            .data(FigureView.fromModelFigure(picture.data().figure()))
            .build();
    }
}
