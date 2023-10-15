package com.zoro.userservice.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(NotFoundException.class)
    private String handleNotFoundException(NotFoundException notFoundException){
        return notFoundException.getMessage();
    }
}
