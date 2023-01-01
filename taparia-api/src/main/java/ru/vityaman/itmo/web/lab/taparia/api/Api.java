package ru.vityaman.itmo.web.lab.taparia.api;

import jakarta.ejb.Startup;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import ru.vityaman.itmo.web.lab.taparia.backend.Backend;

@Startup
@ApplicationPath("/")
public final class Api extends Application {
    public Api() {
        Backend.instance();
    }
}
