package com.nagarro.postservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nagarro.postservice.dto.ErrorDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPostException.class)
    public ResponseEntity<ErrorDTO> handlePostValidationException(InvalidPostException ex) {
        return new ResponseEntity<ErrorDTO>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
