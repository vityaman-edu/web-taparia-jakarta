package ru.vityaman.itmo.web.lab.taparia.backend;

import lombok.Value;
import ru.vityaman.itmo.web.lab.taparia.storage.AccessTokenStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.PictureStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageMigrator;
import ru.vityaman.itmo.web.lab.taparia.storage.TapResultStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.UserAccountStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.FigureSerializer;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.JbdcUserAccountStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.JdbcAccessTokenStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.JdbcPictureStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.JdbcTapResultStorage;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.LiquibaseMigrator;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database.HikariDataSourceFactory;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database.SimpleDatabase;

import javax.sql.DataSource;

@Value
public class Storages {
    UserAccountStorage user;
    PictureStorage picture;
    TapResultStorage tapResult;
    AccessTokenStorage accessToken;

    public static Storages build(Config config) {
        final var log = config.logFactory().newNamedLog("Startup");

        final var dataSource =
            HikariDataSourceFactory.create(config.jdbc());
        log.info("HikariDataSource ready");

        migrateDatabaseUsing(dataSource);
        log.info("Migration ready");

        final var database = new SimpleDatabase(dataSource);

        final var userStorage = new JbdcUserAccountStorage(database);
        log.info("UserStorage ready");

        final var pictureStorage = new JdbcPictureStorage(
            new FigureSerializer.UnsafeJackson(), database
        );
        log.info("PictureStorage ready");

        final var tapResultStorage = new JdbcTapResultStorage(database);
        log.info("TapResultStorage ready");

        final var accessTokenStorage = new JdbcAccessTokenStorage(database);
        log.info("AccessTokenStorage ready");

        return new Storages(
            userStorage,
            pictureStorage,
            tapResultStorage,
            accessTokenStorage
        );
    }

    // TODO: throw runtime exception, maybe create database.migrate
    private static void migrateDatabaseUsing(DataSource dataSource) {
        StorageMigrator databaseMigrator = new LiquibaseMigrator(
            dataSource,
            LiquibaseMigrator.DEFAULT_CHANGELOG_FILE_PATH
        );
        try {
            databaseMigrator.migrate();
        } catch (StorageMigrator.MigrationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
