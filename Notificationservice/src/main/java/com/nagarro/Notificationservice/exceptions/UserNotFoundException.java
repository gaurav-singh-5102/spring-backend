package com.nagarro.Notificationservice.exceptions;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String message) {
        super(message);
    }
}
