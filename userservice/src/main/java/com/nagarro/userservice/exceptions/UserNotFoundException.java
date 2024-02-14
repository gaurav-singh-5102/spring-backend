package com.nagarro.userservice.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User Profile not found.");
    }
}
