package ru.vityaman.itmo.web.lab.taparia;

import lombok.Value;
import ru.vityaman.itmo.web.lab.taparia.figure.Figure;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Value(staticConstructor = "of")
public class Picture {
    Id id;
    UserAccount.Id ownerId;
    Name name;
    Data data;

    @Value(staticConstructor = "of")
    public static class Id {
        long value;

        public Id(long value) {
            if (value <= 0) {
                throw new IllegalArgumentException(String.format(
                    "Picture id must be positive, but %s was given",
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

    @Value(staticConstructor = "of")
    public static class Name {
        private static String regex = "^[A-Za-z0-9]{4,64}$";
        private static Predicate<String> isValidName =
            Pattern.compile(regex).asMatchPredicate();

        String value;

        private Name(String value) {
            if (!isValidName.test(value)) {
                throw new IllegalArgumentException(String.format(
                    "Picture name must match regex %s, but %s was given",
                    regex, value
                ));
            }
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    @Value(staticConstructor = "of")
    public static class Data {
        Figure figure;
    }
}

