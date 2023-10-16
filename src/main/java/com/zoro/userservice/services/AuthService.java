package com.zoro.userservice.services;

import com.zoro.userservice.dtos.LoginRequestDto;
import com.zoro.userservice.dtos.TokenDto;
import com.zoro.userservice.dtos.SignUpRequestDto;
import com.zoro.userservice.dtos.UserDto;
import com.zoro.userservice.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    UserDto signup(SignUpRequestDto signUpRequestDto);
    TokenDto login(LoginRequestDto loginDto) throws NotFoundException;
    String logout(TokenDto tokenDto);
}
