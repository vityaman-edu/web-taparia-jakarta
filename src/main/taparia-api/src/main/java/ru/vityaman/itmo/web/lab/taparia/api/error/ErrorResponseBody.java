package ru.vityaman.itmo.web.lab.taparia.api.error;

import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@NonFinal
@SuperBuilder
@Value
public class ErrorResponseBody {
    int code;
    String message;
}
