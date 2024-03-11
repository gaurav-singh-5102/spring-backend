package com.nagarro.postservice.exceptions;

import java.util.List;

import org.springframework.validation.ObjectError;

public class InvalidNotificationRequestException extends RuntimeException {
	
	public InvalidNotificationRequestException(List<ObjectError> allErrors) {
		
	}
}
