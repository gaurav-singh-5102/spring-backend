package com.nagarro.postservice.exceptions;

public class InvalidAuthorException extends Exception {
    public InvalidAuthorException() {
        super("Invalid Author Details!");
    }
}
