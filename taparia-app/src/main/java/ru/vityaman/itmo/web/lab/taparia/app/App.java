package ru.vityaman.itmo.web.lab.taparia.app;

import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import ru.vityaman.itmo.web.lab.taparia.backend.Backend;

@Startup
@ApplicationScoped
public class App {
    public App() {
        Backend.instance();
    }
}
