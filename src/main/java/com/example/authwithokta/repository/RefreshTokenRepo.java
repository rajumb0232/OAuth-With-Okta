package com.example.authwithokta.repository;

import com.example.authwithokta.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, String> {

}
