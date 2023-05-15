package ru.vityaman.itmo.web.lab.taparia.api.route.picture.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@SuperBuilder
public class CreatePictureResponse {
    @JsonProperty(value = "picture_id", required = true)
    long pictureId;
}
