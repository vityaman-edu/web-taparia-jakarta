package ru.vityaman.itmo.web.lab.taparia.storage;

public interface StorageMigrator {
    void migrate() throws MigrationException;

    class MigrationException extends Exception {
        public MigrationException(Throwable cause) {
            super(cause);
        }
    }
}
