package ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database;

import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class SimpleDatabase implements Database {
    private final DataSource ds;

    public SimpleDatabase(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public <T> T withConnection(Action<T> action)
        throws StorageException {
        try (Connection connection = ds.getConnection()) {
            return action.execute(DSL.using(connection, SQLDialect.POSTGRES));
        } catch (SQLException | DataAccessException e) {
            throw new StorageException(e);
        }
    }
}
