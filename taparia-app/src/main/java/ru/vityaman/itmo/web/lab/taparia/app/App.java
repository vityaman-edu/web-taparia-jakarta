package ru.vityaman.itmo.web.lab.taparia.app;

import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import ru.vityaman.itmo.web.lab.taparia.backend.Backend;

import java.util.concurrent.atomic.AtomicReference;

@Startup
@ApplicationScoped
public class App {
    private static final AtomicReference<Backend> BACKEND =
            new AtomicReference<>();

    public App() {
        backend();
    }

    public static Backend backend() {
        synchronized (BACKEND) {
            if (BACKEND.get() == null) {
                BACKEND.set(new Backend("app"));
            }
            return BACKEND.get();
        }
    }
}
