package ru.vityaman.itmo.web.lab.taparia.storage.jdbc.database;

import org.jooq.DSLContext;
import ru.vityaman.itmo.web.lab.taparia.storage.StorageException;

public interface Database {
    <T> T withConnection(Action<T> action) throws StorageException;

    @FunctionalInterface
    interface Action<T> {
        T execute(DSLContext context) throws StorageException;
    }
}
