package com.zoro.userservice.dtos;

import com.zoro.userservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDto {
    private String email;
    private List<Role> roles;
}
