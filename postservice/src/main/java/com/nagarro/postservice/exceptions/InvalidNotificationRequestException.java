package com.nagarro.postservice.exceptions;

import java.util.List;

import org.springframework.validation.ObjectError;

public class InvalidNotificationRequestException extends RuntimeException {

	public InvalidNotificationRequestException(String message ) {
		super(message);
	}

	public InvalidNotificationRequestException(List<ObjectError> allErrors) {
		// TODO Auto-generated constructor stub
	}
}
