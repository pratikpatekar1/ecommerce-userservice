package com.zoro.userservice.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zoro.userservice.models.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Getter
@Setter
@JsonDeserialize(as = CustomSpringGrantedAuthority.class)
public class CustomSpringGrantedAuthority implements GrantedAuthority, Serializable {
    public CustomSpringGrantedAuthority(){}
    Role role;
    public CustomSpringGrantedAuthority(Role role){
        this.role = role;
    }
    @Override
    @JsonIgnore
    public String getAuthority() {
        return role.getRole();
    }
}
