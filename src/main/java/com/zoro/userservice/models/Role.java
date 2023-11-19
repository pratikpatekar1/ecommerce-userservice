package com.zoro.userservice.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonDeserialize(as = Role.class)
public class Role extends BaseModel{
    private String role;
}
