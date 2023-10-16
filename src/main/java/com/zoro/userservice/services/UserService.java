package com.zoro.userservice.services;

import com.zoro.userservice.dtos.UserDto;
import com.zoro.userservice.models.User;
import com.zoro.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserDto getUserDetails(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
