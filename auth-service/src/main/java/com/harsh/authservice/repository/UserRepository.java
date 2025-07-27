package com.harsh.authservice.repository;

import com.harsh.authservice.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository <User, UUID>
{
    Optional<User> findByEmail(String email);
}
