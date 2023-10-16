package com.zoro.userservice.controllers;

import com.zoro.userservice.dtos.LoginDto;
import com.zoro.userservice.dtos.TokenDto;
import com.zoro.userservice.dtos.SignUpRequestDto;
import com.zoro.userservice.dtos.UserDto;
import com.zoro.userservice.exceptions.NotFoundException;
import com.zoro.userservice.models.User;
import com.zoro.userservice.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService userService){
        this.authService = userService;
    }

    @PostMapping("/signup")
    public UserDto singup(@RequestBody SignUpRequestDto signUpRequestDto){
        return authService.signup(signUpRequestDto);
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto loginDto) throws NotFoundException {
        return authService.login(loginDto);
    }

    @PostMapping("/logout")
    public String logout(@RequestBody TokenDto tokenDto) {
        return authService.logout(tokenDto);
    }

}
