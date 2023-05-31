package ru.vityaman.itmo.web.lab.taparia.api;

import jakarta.ejb.Startup;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import ru.vityaman.itmo.web.lab.taparia.backend.Backend;

import java.util.concurrent.atomic.AtomicReference;

@Startup
@ApplicationPath("/")
public final class Api extends Application {
    private static final AtomicReference<Backend> BACKEND =
            new AtomicReference<>();

    public Api() {
        backend();
    }

    public static Backend backend() {
        synchronized (BACKEND) {
            if (BACKEND.get() == null) {
                BACKEND.set(new Backend("api"));
            }
            return BACKEND.get();
        }
    }
}
