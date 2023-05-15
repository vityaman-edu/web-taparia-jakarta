package ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database;

public interface JdbcConfig {
    String database();

    String host();

    int port();

    String instance();

    String username();

    String password();

    default String url() {
        return String.format(
            "jdbc:%s://%s:%d/%s",
            database(), host(), port(), instance()
        );
    }
}
