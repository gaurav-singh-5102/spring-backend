package com.nagarro.Commentservice.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nagarro.Commentservice.DTO.ErrorDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({InvalidCommentException.class, CommentNotFoundException.class})
    public ResponseEntity<ErrorDTO> handlePostValidationException(Exception ex) {
        return new ResponseEntity<ErrorDTO>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
	@ExceptionHandler({InvalidRequestException.class})
    public ResponseEntity<ErrorDTO> handleInvalidRequestException(Exception ex) {
        return new ResponseEntity<ErrorDTO>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
