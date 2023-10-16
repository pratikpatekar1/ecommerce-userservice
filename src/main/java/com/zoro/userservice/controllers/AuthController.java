package com.zoro.userservice.controllers;

import com.zoro.userservice.dtos.LoginRequestDto;
import com.zoro.userservice.dtos.TokenDto;
import com.zoro.userservice.dtos.SignUpRequestDto;
import com.zoro.userservice.dtos.UserDto;
import com.zoro.userservice.exceptions.NotFoundException;
import com.zoro.userservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDto> singup(@RequestBody SignUpRequestDto signUpRequestDto){
        return new ResponseEntity<>(authService.signup(signUpRequestDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) throws NotFoundException {
        return new ResponseEntity<>(authService.login(loginRequestDto),HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody TokenDto tokenDto) {
        return new ResponseEntity<>(authService.logout(tokenDto),HttpStatus.OK);
    }

}
