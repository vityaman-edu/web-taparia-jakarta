package ru.vityaman.itmo.web.lab.taparia.api.route.auth.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@SuperBuilder
public class AccessTokenView {
    @Positive
    @JsonProperty(value = "user_id", required = true)
    long userId;

    @Size(min = 32, max = 32)
    @JsonProperty(value = "secret", required = true)
    String secret;
}
