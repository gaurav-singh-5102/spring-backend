package com.nagarro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nagarro.dto.ErrorDto;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
	    BindingResult bindingResult = ex.getBindingResult();

	    // You can iterate over field errors and create a custom error message
	    StringBuilder errorMessage = new StringBuilder("Validation errors: ");
	    for (FieldError fieldError : bindingResult.getFieldErrors()) {
	        errorMessage.append(fieldError.getDefaultMessage()).append("; ");
	    }

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
	}
	
	@ExceptionHandler({UserAlreadyExistsException.class})
	public ResponseEntity<ErrorDto> handleResponseException(Exception ex){
		return new ResponseEntity<ErrorDto>(new ErrorDto(ex.getMessage()), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler({OtpException.class})
	public ResponseEntity<ErrorDto> incorrectOtpException(Exception ex){
		return new ResponseEntity<ErrorDto>(new ErrorDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
