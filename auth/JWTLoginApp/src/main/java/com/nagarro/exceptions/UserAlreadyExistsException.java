package com.nagarro.exceptions;

public class UserAlreadyExistsException extends Exception {

	public UserAlreadyExistsException() {
		super("User with email Already Exists");
	}
}
