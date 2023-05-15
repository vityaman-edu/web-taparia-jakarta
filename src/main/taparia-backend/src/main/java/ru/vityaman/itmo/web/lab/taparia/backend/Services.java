package ru.vityaman.itmo.web.lab.taparia.backend;

import lombok.SneakyThrows;
import lombok.Value;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.AuthService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.PictureService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.SecretsService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.TapResultService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.UserService;
import ru.vityaman.itmo.web.lab.taparia.logic.basic.BasicAccessTokenService;
import ru.vityaman.itmo.web.lab.taparia.logic.basic.BasicAuthService;
import ru.vityaman.itmo.web.lab.taparia.logic.basic.BasicPictureService;
import ru.vityaman.itmo.web.lab.taparia.logic.basic.BasicSecretsService;
import ru.vityaman.itmo.web.lab.taparia.logic.basic.BasicTapResultService;
import ru.vityaman.itmo.web.lab.taparia.logic.basic.BasicUserService;
import ru.vityaman.itmo.web.lab.taparia.logic.logging.LoggingPictureService;
import ru.vityaman.itmo.web.lab.taparia.logic.logging.LoggingUserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Value
public class Services {
    SecretsService password;
    AuthService auth;
    UserService user;
    PictureService picture;
    TapResultService tapResult;

    @SneakyThrows({NoSuchAlgorithmException.class})
    public static Services build(Config config, Storages storage) {
        final var log = config.logFactory().newNamedLog("Startup");

        final var secretsService =
            new BasicSecretsService(
                MessageDigest.getInstance("SHA-256"),
                new SecureRandom(),
                config.clock()
            );
        final var tokenService =
            new BasicAccessTokenService(
                secretsService,
                storage.accessToken(),
                config.clock()
            );

        final var authService =
            new BasicAuthService(
                storage.user(),
                secretsService,
                tokenService
            );

        final var userService =
            new LoggingUserService(
                config.logFactory(),
                new BasicUserService(
                    storage.user(),
                    authService
                )
            );
        log.info("UserService ready");


        final var pictureService =
            new LoggingPictureService(
                config.logFactory(),
                new BasicPictureService(storage.picture())
            );
        log.info("PictureService ready");

        final var tapResultService =
            new BasicTapResultService(
                pictureService,
                storage.tapResult()
            );

        return new Services(
            secretsService,
            authService,
            userService,
            pictureService,
            tapResultService
        );
    }
}
