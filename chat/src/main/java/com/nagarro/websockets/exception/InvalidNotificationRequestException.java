package com.nagarro.websockets.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class InvalidNotificationRequestException extends Exception {

	public InvalidNotificationRequestException(List<ObjectError> allErrors) {
		super("Validation failed for Notification : " + allErrors);
	}
}
