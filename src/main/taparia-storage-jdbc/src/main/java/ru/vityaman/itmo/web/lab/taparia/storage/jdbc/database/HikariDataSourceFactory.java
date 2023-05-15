package ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.experimental.UtilityClass;

import javax.sql.DataSource;
import java.util.Map;

@UtilityClass
public final class HikariDataSourceFactory {
    private static final Map<String, String> PROPERTIES = Map.of(
        "cachePrepStmts", "true",
        "prepStmtCacheSize", "250",
        "prepStmtCacheSqlLimit", "2048"
    );

    public static DataSource create(JdbcConfig config) {
        final var hikari = new HikariConfig();
        hikari.setJdbcUrl(config.url());
        hikari.setUsername(config.username());
        hikari.setPassword(config.password());
        PROPERTIES.forEach(hikari::addDataSourceProperty);
        return new HikariDataSource(hikari);
    }
}
