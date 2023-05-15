package ru.vityaman.itmo.web.lab.taparia;

import lombok.Value;
import ru.vityaman.itmo.web.lab.taparia.figure.primitive.Point;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

@Value(staticConstructor = "of")
public class TapResult {
    Id id;
    UserAccount.Id ownerId;
    Picture.Id pictureId;
    Point point;
    Status status;

    public TapResult withId(Id id) {
        return TapResult.of(id, ownerId, pictureId, point, status);
    }

    @Value(staticConstructor = "of")
    public static class Id {
        long value;

        public Id(long value) {
            if (value <= 0) {
                throw new IllegalArgumentException(String.format(
                    "User account id must be positive, but %s was given",
                    value
                ));
            }
            this.value = value;
        }

        public static Id dummy() {
            return Id.of(Long.MAX_VALUE);
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    public enum Status {
        HIT("HIT"),
        MISS("MISS");

        private final String asString;

        Status(String asString) {
            this.asString = asString;
        }

        public String asString() {
            return asString;
        }

        public static Status from(String string) {
            return switch (string) {
                case "HIT" -> HIT;
                case "MISS" -> MISS;
                default -> throw new IllegalArgumentException(String.format(
                    "No such status: %s (HIT, MISS)", string
                ));
            };
        }
    }

    @Value(staticConstructor = "of")
    public static class Draft {
        UserAccount.Id ownerId;
        Picture.Id pictureId;
        Point point;
    }
}
