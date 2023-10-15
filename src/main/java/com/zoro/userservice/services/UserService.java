package com.zoro.userservice.services;

import com.zoro.userservice.dtos.LoginDto;
import com.zoro.userservice.dtos.TokenDto;
import com.zoro.userservice.dtos.UserRegistrationDto;
import com.zoro.userservice.exceptions.NotFoundException;
import com.zoro.userservice.models.User;
import com.zoro.userservice.repositories.UserRepository;

public interface UserService {

    User createUser(UserRegistrationDto userRegistrationDto);
    TokenDto login(LoginDto loginDto) throws NotFoundException;
    String logout(TokenDto tokenDto);
}
