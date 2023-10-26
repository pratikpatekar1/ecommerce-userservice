package com.zoro.userservice.services;

import com.zoro.userservice.dtos.LoginRequestDto;
import com.zoro.userservice.dtos.SignUpRequestDto;
import com.zoro.userservice.dtos.UserDto;
import com.zoro.userservice.exceptions.BadCredentialsException;
import com.zoro.userservice.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    UserDto signup(SignUpRequestDto signUpRequestDto);
    ResponseEntity<UserDto> login(LoginRequestDto loginDto) throws NotFoundException, BadCredentialsException;
    String logout(String token);
    Boolean validateToken(String token);
}
