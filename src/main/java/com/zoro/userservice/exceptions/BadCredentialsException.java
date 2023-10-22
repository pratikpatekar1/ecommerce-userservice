package com.zoro.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class BadCredentialsException extends Exception{
    public BadCredentialsException(String message){
        super(message);
    }
}
