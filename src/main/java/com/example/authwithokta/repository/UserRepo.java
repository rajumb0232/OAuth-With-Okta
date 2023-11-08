package com.example.authwithokta.repository;

import com.example.authwithokta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByUserEmail(String email);
}
