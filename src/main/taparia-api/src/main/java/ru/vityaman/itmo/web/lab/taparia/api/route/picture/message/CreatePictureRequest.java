package ru.vityaman.itmo.web.lab.taparia.api.route.picture.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure.FigureView;

@Jacksonized
@Value
@SuperBuilder
public class CreatePictureRequest {
    @JsonProperty(value = "name", required = true)
    String name;

    @JsonProperty(value = "data", required = true)
    FigureView data;
}
