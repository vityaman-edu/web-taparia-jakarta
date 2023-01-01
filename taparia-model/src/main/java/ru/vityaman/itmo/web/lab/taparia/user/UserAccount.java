package ru.vityaman.itmo.web.lab.taparia.user;

import lombok.Value;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Value(staticConstructor = "of")
public class UserAccount {
    Id id;
    Name name;
    Password password;

    public UserAccount withId(UserAccount.Id id) {
        return UserAccount.of(id, name(), password());
    }

    @Value(staticConstructor = "of")
    public static class Id {
        long value;

        public Id(long value) {
            if (value <= 0) {
                throw new IllegalArgumentException(String.format(
                    "User account id must be positive, but '%s' was given",
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
        private static String regex = "^[A-Za-z0-9]{4,32}$";
        private static Predicate<String> isValidName =
            Pattern.compile(regex).asMatchPredicate();

        String value;

        private Name(String value) {
            if (!isValidName.test(value)) {
                throw new IllegalArgumentException(String.format(
                    "User account name must match regex '%s', "
                    + "but '%s' was given",
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

    @Value(staticConstructor = "fromHashAndSalt")
    public static class Password {
        byte[] hash;
        byte[] salt;

        // TODO: arguments can be accidentally swapped
        private Password(byte[] hash, byte[] salt) {
            this.hash = Arrays.copyOf(hash, hash.length);
            this.salt = Arrays.copyOf(salt, salt.length);
        }

        public byte[] hash() {
            return Arrays.copyOf(hash, hash.length);
        }

        public byte[] salt() {
            return Arrays.copyOf(salt, salt.length);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null) {
                return false;
            }
            if (getClass() != other.getClass()) {
                return false;
            }
            Password that = (Password) other;
            return Arrays.equals(this.hash, that.hash)
                && Arrays.equals(this.salt, that.salt);
        }

        @Override
        public int hashCode() {
            return Objects.hash(
                Arrays.hashCode(this.hash),
                Arrays.hashCode(this.salt)
            );
        }
    }
}
