package com.example.promise.domain.challenge.repository;

import com.example.promise.domain.challenge.entity.ChallengeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ChallengeGroupRepository extends JpaRepository<ChallengeGroup, Long> {
    Optional<ChallengeGroup> findByChallengeDateAndRequiredDoseCount(LocalDate date, int count);

}
