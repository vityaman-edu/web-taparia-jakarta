package ru.vityaman.itmo.web.lab.taparia.backend;

import ru.vityaman.itmo.web.lab.taparia.common.logging.LogFactory;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.AuthService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.PictureService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.TapResultService;
import ru.vityaman.itmo.web.lab.taparia.logic.abstraction.UserService;
import ru.vityaman.itmo.web.lab.taparia.logic.secured.SecuredPictureService;
import ru.vityaman.itmo.web.lab.taparia.user.UserAccessToken;

public final class Backend {
    private final Services service;
    private final Config config;

    public Backend(String serviceName) {
        this.config = Config.build(serviceName);
        final var log = config.logFactory().newNamedLog("Startup");
        log.info("Config ready");

        final var storages = Storages.build(config);
        log.info("Storages ready");

        this.service = Services.build(config, storages);
        log.info("Services ready");
    }

    public AuthService authService() {
        return service.auth();
    }

    public UserService userService() {
        return service.user();
    }

    public PictureService pictureService(UserAccessToken token) {
        return new SecuredPictureService(
            authService(),
            service.picture(),
            token
        );
    }

    public TapResultService tapResultService() {
        return service.tapResult();
    }

    public LogFactory logFactory() {
        return config.logFactory();
    }
}
