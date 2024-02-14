package com.nagarro.userservice.exceptions;

public class UserAlreadyExists extends Exception {
    public UserAlreadyExists() {
        super("User Already Exists");
    }
}
