package com.zoro.userservice.services;

import com.zoro.userservice.dtos.LoginDto;
import com.zoro.userservice.dtos.TokenDto;
import com.zoro.userservice.dtos.SignUpRequestDto;
import com.zoro.userservice.dtos.UserDto;
import com.zoro.userservice.exceptions.NotFoundException;
import com.zoro.userservice.models.User;

public interface AuthService {

    UserDto signup(SignUpRequestDto signUpRequestDto);
    TokenDto login(LoginDto loginDto) throws NotFoundException;
    String logout(TokenDto tokenDto);
}
