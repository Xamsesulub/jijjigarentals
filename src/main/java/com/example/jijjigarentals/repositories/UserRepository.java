package com.example.jijjigarentals.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jijjigarentals.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
       boolean existsByEmail(String email);  // ← LEGG TIL DENNE
}