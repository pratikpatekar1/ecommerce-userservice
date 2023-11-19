package com.zoro.userservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@JsonDeserialize(as = User.class)
public class User extends BaseModel{
    private String email;
    private String Password;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore
    private List<Session> sessions;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();
}
