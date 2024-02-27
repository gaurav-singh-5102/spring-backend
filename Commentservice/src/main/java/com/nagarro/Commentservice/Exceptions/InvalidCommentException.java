package com.nagarro.Commentservice.Exceptions;

import java.util.List;

import org.springframework.validation.ObjectError;

public class InvalidCommentException extends Exception {
	public InvalidCommentException(List<ObjectError> errors) {
        super("Validation failed for post : " + errors);
    }
}
