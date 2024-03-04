package com.nagarro.Commentservice.Exceptions;

import java.util.List;

import org.springframework.validation.ObjectError;

public class InvalidNotificationRequestException extends Exception {

	public InvalidNotificationRequestException(String message ) {
		super(message);
	}
	public InvalidNotificationRequestException(List<ObjectError> allErrors) {
	}
}
