package com.example.promise.domain.user.repository;

import com.example.promise.domain.user.entity.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NormalUserRepository extends JpaRepository<NormalUser,String> {
    Optional<NormalUser> findByEmail(String email);
    Optional<NormalUser> findById(Long id);
    Optional<NormalUser> findByName(String name);

    Boolean existsByEmail(String email);
}
