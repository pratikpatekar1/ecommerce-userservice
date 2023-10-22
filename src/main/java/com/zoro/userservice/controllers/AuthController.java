package com.zoro.userservice.controllers;

import com.zoro.userservice.dtos.LoginRequestDto;
import com.zoro.userservice.dtos.SignUpRequestDto;
import com.zoro.userservice.dtos.UserDto;
import com.zoro.userservice.exceptions.BadCredentialsException;
import com.zoro.userservice.exceptions.NotFoundException;
import com.zoro.userservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws NotFoundException, BadCredentialsException {
        return authService.login(loginRequestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue(name="auth-token")  String token) {
        return new ResponseEntity<>(authService.logout(token),HttpStatus.OK);
    }

}
