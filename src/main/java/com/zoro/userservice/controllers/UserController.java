package com.zoro.userservice.controllers;

import com.zoro.userservice.dtos.LoginDto;
import com.zoro.userservice.dtos.TokenDto;
import com.zoro.userservice.dtos.UserRegistrationDto;
import com.zoro.userservice.exceptions.NotFoundException;
import com.zoro.userservice.models.User;
import com.zoro.userservice.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody UserRegistrationDto userRegistrationDto){
        return userService.createUser(userRegistrationDto);
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto loginDto) throws NotFoundException {
        return userService.login(loginDto);
    }

    @PostMapping("/logout")
    public String logout(@RequestBody TokenDto tokenDto) {
        return userService.logout(tokenDto);
    }

}
