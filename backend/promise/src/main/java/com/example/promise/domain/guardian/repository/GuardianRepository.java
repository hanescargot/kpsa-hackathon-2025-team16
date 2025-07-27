package com.example.promise.domain.guardian.repository;

import com.example.promise.domain.guardian.entity.Guardian;
import com.example.promise.domain.user.entity.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {
    Optional<Guardian> findByUser(NormalUser user);
}