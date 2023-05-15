package ru.vityaman.itmo.web.lab.taparia.logic.basic;

import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccount;
import ru.vityaman.itmo.web.lab.taparia.user.UserCredentials;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.SecretsService;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class BasicSecretsService implements SecretsService {
    // TODO: hardcoded values
    private static final char[] TOKEN_ALPHABET = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    private static final int TOKEN_LENGTH = 32;
    private static final Duration TOKEN_TTL = Duration.ofDays(1);
    private static final int SALT_SIZE = 16;

    private final MessageDigest digest;
    private final SecureRandom random;
    private final Clock clock;

    public BasicSecretsService(
        MessageDigest digest,
        SecureRandom random,
        Clock clock
    ) {
        this.digest = digest;
        this.random = random;
        this.clock = clock;
    }

    @Override
    public UserAccount.Password hashPassword(
        UserCredentials.Password password
    ) {
        return hashPassword(password, generateSalt());
    }

    @Override
    public boolean testPasswords(
        UserCredentials.Password given,
        UserAccount.Password actual
    ) {
        return hashPassword(given, actual.salt()).equals(actual);
    }

    @Override
    public UserAccessToken generateAccessToken(UserAccount.Id userId) {
        return UserAccessToken.of(
            userId,
            UserAccessToken.Body.of(randomStringWithLength(TOKEN_LENGTH)),
            LocalDateTime.now(clock),
            TOKEN_TTL
        );
    }

    private UserAccount.Password hashPassword(
        UserCredentials.Password password, byte[] salt
    ) {
        byte[] hashedPassword;
        synchronized (digest) {
            digest.update(salt);
            hashedPassword =
                digest.digest(password.value().getBytes(UTF_8));
        }
        return UserAccount.Password.fromHashAndSalt(hashedPassword, salt);
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return salt;
    }

    private String randomStringWithLength(final int length) {
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(TOKEN_ALPHABET.length);
            buffer.append(TOKEN_ALPHABET[index]);
        }
        return buffer.toString();
    }
}
