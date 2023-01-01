package ru.vityaman.itmo.web.lab.taparia.user;

import lombok.Value;

@Value(staticConstructor = "of")
public class UserCredentials {
    UserAccount.Name username;
    Password password;

    public static UserCredentials from(String username, String password) {
        return UserCredentials.of(
            UserAccount.Name.of(username),
            Password.of(password)
        );
    }

    @Value(staticConstructor = "of")
    public static class Password {
        String value;

        @Override
        public String toString() {
            return "<password>";
        }
    }
}
