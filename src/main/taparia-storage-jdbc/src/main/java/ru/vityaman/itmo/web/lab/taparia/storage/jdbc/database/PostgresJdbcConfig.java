package ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database;


import java.util.function.Supplier;

public final class PostgresJdbcConfig implements JdbcConfig {

    private final String host;
    private final int port;
    private final String instance;
    private final Supplier<String> username;
    private final Supplier<String> password;

    public PostgresJdbcConfig(
        String host,
        int port,
        String instance,
        Supplier<String> username,
        Supplier<String> password
    ) {
        this.host = host;
        this.port = port;
        this.instance = instance;
        this.username = username;
        this.password = password;
    }

    @Override
    public String database() {
        return "postgresql";
    }

    @Override
    public String host() {
        return host;
    }

    @Override
    public int port() {
        return port;
    }

    @Override
    public String instance() {
        return instance;
    }

    @Override
    public String username() {
        // System.getenv("POSTGRES_USERNAME");
        return username.get();
    }

    @Override
    public String password() {
        // return System.getenv("POSTGRES_PASSWORD");
        return password.get();
    }

    @Override
    public String toString() {
        return String.format(
            "PostgresJdbcConfig(%s, %s, %s)",
            url(), username(), password()
        );
    }
}
