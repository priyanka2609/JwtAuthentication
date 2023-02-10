package com.pri.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pri.auth.payload.JwtAuthRequest;

@Repository
public interface UserRepo extends JpaRepository<JwtAuthRequest, Integer>{

	Optional<JwtAuthRequest> findByUsername(String email);
}
