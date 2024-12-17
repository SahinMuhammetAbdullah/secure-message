package com.secmes.secure_message.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secmes.secure_message.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
