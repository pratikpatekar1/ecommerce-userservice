package com.zoro.userservice.services;

import com.zoro.userservice.dtos.UserDto;
import com.zoro.userservice.exceptions.NotFoundException;
import com.zoro.userservice.models.Role;
import com.zoro.userservice.models.User;
import com.zoro.userservice.repositories.RoleRepository;
import com.zoro.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public UserDto getUserDetails(String id) {
        Optional<User> savedUser = userRepository.findById(UUID.fromString(id));
        if(savedUser.isEmpty()){
            return null;
        }
        User user = savedUser.get();
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

    public String addRoleToUser(String userId, String roleId) throws NotFoundException {
        Optional<User> savedUser = userRepository.findById(UUID.fromString(userId));
        if(savedUser.isEmpty()){
            throw new NotFoundException("User not found");
        }
        User user = savedUser.get();
        Optional<Role> roleOptional = roleRepository.findById(UUID.fromString(roleId));
        if(roleOptional.isEmpty()){
            throw new NotFoundException("Role not found");
        }
        Role role = roleOptional.get();
        user.getRoles().add(role);
        userRepository.save(user);
        return "Role added to user";
    }
}
