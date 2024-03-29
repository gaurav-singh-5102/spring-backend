package com.nagarro.videoservice.exception;

public class StorageException extends Exception {
    public StorageException() {
        super("Could not upload video.");
    }
}
