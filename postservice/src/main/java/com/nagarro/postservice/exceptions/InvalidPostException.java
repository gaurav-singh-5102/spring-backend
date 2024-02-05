package com.nagarro.postservice.exceptions;

import java.util.List;

import org.springframework.validation.ObjectError;

public class InvalidPostException extends Exception {
    public InvalidPostException(List<ObjectError> errors) {
        super("Validation failed for post : " + errors);
    }
}
