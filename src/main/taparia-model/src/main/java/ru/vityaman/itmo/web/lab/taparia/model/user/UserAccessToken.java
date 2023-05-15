package ru.vityaman.itmo.web.lab.taparia.user;

import lombok.Value;

import java.time.Duration;
import java.time.LocalDateTime;

@Value(staticConstructor = "of")
public class UserAccessToken {
    UserAccount.Id userId;
    Body secret;
    LocalDateTime createdAt;
    Duration ttl;

    @Value(staticConstructor = "of")
    public static class Body {
        String value;
    }

    public static UserAccessToken from(long userId, String body) {
        return UserAccessToken.of(
            UserAccount.Id.of(userId),
            UserAccessToken.Body.of(body),
            LocalDateTime.MIN,
            Duration.ZERO
        );
    }

    public boolean willBeExpiredAt(LocalDateTime time) {
        return createdAt.plus(ttl).isBefore(time);
    }

    @Override
    public String toString() {
        return String.format("%s:<access token body>", userId);
    }
}
