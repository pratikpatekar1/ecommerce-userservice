package com.zoro.userservice.services;

import com.zoro.userservice.models.Role;
import com.zoro.userservice.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    public Role createRole(Role role) {
        Role savedRole = roleRepository.save(role);
        return savedRole;
    }
}
