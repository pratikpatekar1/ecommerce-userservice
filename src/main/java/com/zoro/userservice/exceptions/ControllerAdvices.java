package com.zoro.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<String> handleNotFoundException(NotFoundException notFoundException){
        return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<String> handleBadCredentialsException(BadCredentialsException badCredentialsException){
        return new ResponseEntity<>(badCredentialsException.getMessage(), HttpStatus.OK);
    }
}
