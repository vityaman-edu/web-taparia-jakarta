package ru.vityaman.itmo.web.lab.taparia.storage.jdbc;

import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;
import ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database.Database;

public abstract class JdbcStorage {
    private final Database database;

    protected JdbcStorage(Database database) {
        this.database = database;
    }

    public final <T> T withConnection(Database.Action<T> action)
        throws StorageException {
        return database.withConnection(action);
    }
}
