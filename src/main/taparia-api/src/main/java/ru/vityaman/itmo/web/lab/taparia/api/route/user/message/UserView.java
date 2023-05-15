package ru.vityaman.itmo.web.lab.taparia.api.route.user.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Value
@SuperBuilder
public class UserView {
    @JsonProperty(value = "id", required = true)
    long id;

    @JsonProperty(value = "name", required = true)
    String name;
}
