package com.zoro.userservice.repositories;

import com.zoro.userservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID>{

    Session findByToken(String token);
}
