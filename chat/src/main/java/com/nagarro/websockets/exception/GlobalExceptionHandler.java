package com.nagarro.websockets.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nagarro.websockets.dto.ErrorDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
 
	@ExceptionHandler({ChatMessageValidationException.class, Exception.class, Throwable.class})
    public ResponseEntity<String> handleChatMessageValidationException(ChatMessageValidationException e) {
        return new ResponseEntity<>("Error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(InvalidNotificationRequestException.class)
    public ResponseEntity<ErrorDTO> handleInvalidNotificationRequestException(InvalidNotificationRequestException ex) {
        return new ResponseEntity<ErrorDTO>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
	
}

