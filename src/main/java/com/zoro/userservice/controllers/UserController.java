package com.zoro.userservice.controllers;

import com.zoro.userservice.dtos.UserDto;
import com.zoro.userservice.exceptions.NotFoundException;
import com.zoro.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable String id) throws NotFoundException {
        UserDto userDto = userService.getUserDetails(id);
        if(userDto == null){
            throw new NotFoundException("User not found");
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/add/role")
    public ResponseEntity<String>addRoleToUser(@RequestParam("userId") String userId, @RequestParam("roleId") String roleId) throws NotFoundException {
        return new ResponseEntity<>(userService.addRoleToUser(userId, roleId),HttpStatus.OK);
    }
}
