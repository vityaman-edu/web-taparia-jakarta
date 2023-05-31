package ru.vityaman.itmo.web.lab.taparia.backend;

import lombok.SneakyThrows;
import lombok.Value;
import ru.vityaman.itmo.web.lab.taparia.common.monitoring.mbean.MBeanMonitoringService;
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
import ru.vityaman.itmo.web.lab.taparia.logic.monitored.MonitoredTapResultService;
import ru.vityaman.itmo.web.lab.taparia.logic.strange.Last5PointsFigureAreaCalculatingTapResultService;
import ru.vityaman.itmo.web.lab.taparia.logic.strange.Last5PointsFigureAreaMXBean;

import javax.management.JMException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
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

    @SneakyThrows({
            NoSuchAlgorithmException.class,
            JMException.class,
    })
    public static Services build(Config config, Storages storage) {
        final var log = config.logFactory().newNamedLog("Startup");

        final var mbeanServer = ManagementFactory.getPlatformMBeanServer();

        final var monitoring = new MBeanMonitoringService(mbeanServer)
                .of(config.serviceName());

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

        final var area = new Last5PointsFigureAreaMXBean.Instance(0);

        mbeanServer.registerMBean(area, new ObjectName(String.format(
                "%s:type=%s,name=%s",
                area.getClass().getPackageName(),
                area.getClass().getCanonicalName(),
                config.serviceName() + ".Last5PointsFigureArea"
        )));

        final var tapResultService =
                new Last5PointsFigureAreaCalculatingTapResultService(
                        new MonitoredTapResultService(
                                new BasicTapResultService(
                                        pictureService,
                                        storage.tapResult()
                                ),
                                monitoring.of("TapResultService")
                        ),
                        area::setValue
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
