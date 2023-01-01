package ru.vityaman.itmo.web.lab.taparia.api.route.taps.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import ru.vityaman.itmo.web.lab.taparia.TapResult;
import ru.vityaman.itmo.web.lab.taparia.common.model.serialize.figure.VectorView;


@Jacksonized
@Value
@SuperBuilder
public class TapResultView {
    @JsonProperty(value = "id", required = true)
    long id;

    @JsonProperty(value = "owner_id", required = true)
    long ownerId;

    @JsonProperty(value = "picture_id", required = true)
    long pictureId;

    @JsonProperty(value = "point", required = true)
    VectorView point;

    @JsonProperty(value = "status", required = true)
    String status;

    public static TapResultView fromModel(TapResult result) {
        return TapResultView.builder()
            .id(result.id().value())
            .ownerId(result.ownerId().value())
            .pictureId(result.pictureId().value())
            .point(VectorView.fromModel(result.point()))
            .status(result.status().asString())
            .build();
    }
}
