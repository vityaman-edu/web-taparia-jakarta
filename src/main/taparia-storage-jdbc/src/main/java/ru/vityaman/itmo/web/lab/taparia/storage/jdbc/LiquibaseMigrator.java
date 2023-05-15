package ru.vityaman.itmo.web.lab.taparia.storage.jdbc;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageMigrator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class LiquibaseMigrator implements StorageMigrator {
    public static final String DEFAULT_CHANGELOG_FILE_PATH =
        "database/migration/sql/schema.sql";

    private static final String STAGE = "development";

    private final DataSource dataSource;
    private final String changelogFilePath;

    public LiquibaseMigrator(DataSource dataSource, String changelogFilePath) {
        this.dataSource = dataSource;
        this.changelogFilePath = changelogFilePath;
    }

    public void migrate() throws MigrationException {
        ResourceAccessor resourceAccessor =
            new ClassLoaderResourceAccessor(getClass().getClassLoader());
        try (Connection connection = dataSource.getConnection()) {
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            liquibase.database.Database db = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(jdbcConnection);
            Liquibase liquibase =
                new Liquibase(changelogFilePath, resourceAccessor, db);
            liquibase.update(STAGE);
        } catch (SQLException | LiquibaseException e) {
            throw new MigrationException(e);
        }
    }
}
