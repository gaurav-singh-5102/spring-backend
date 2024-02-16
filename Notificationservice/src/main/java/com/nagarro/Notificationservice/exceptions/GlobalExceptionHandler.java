package com.nagarro.Notificationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nagarro.Notificationservice.dto.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException ex) {
	    BindingResult bindingResult = ex.getBindingResult();

	    // You can iterate over field errors and create a custom error message
	    StringBuilder errorMessage = new StringBuilder("Validation errors: ");
	    for (FieldError fieldError : bindingResult.getFieldErrors()) {
	        errorMessage.append(fieldError.getDefaultMessage()).append("; ");
	    }

	    ErrorDto errorDto = new ErrorDto(errorMessage.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
//	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
