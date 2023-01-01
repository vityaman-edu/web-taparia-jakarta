package ru.vityaman.itmo.web.lab.taparia.backend;

import lombok.Value;
import ru.vityaman.itmo.web.lab.taparia.common.logging.LogFactory;
import ru.vityaman.itmo.web.lab.taparia.common.logging.jboss.JbossLogFactory;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database.JdbcConfig;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database.PostgresJdbcConfig;

import java.time.Clock;

@Value
public class Config {
    Clock clock;
    LogFactory logFactory;
    JdbcConfig jdbc;

    public static Config build() {
        final var host = "database";
        final var port = 5432;
        final var db = "postgres";

        final var jdbcConfig = new PostgresJdbcConfig(
            host,
            port,
            db,
            () -> System.getenv("POSTGRES_USERNAME"),
            () -> System.getenv("POSTGRES_PASSWORD")
        );

        final var logFactory = new JbossLogFactory();

        return new Config(
            Clock.systemUTC(),
            logFactory,
            jdbcConfig
        );
    }
}
