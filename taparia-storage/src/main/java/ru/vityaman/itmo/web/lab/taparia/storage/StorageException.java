package ru.vityaman.itmo.web.lab.taparia.storage;

public class StorageException extends Exception {
    public StorageException(String message) {
        super(message);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }
}
