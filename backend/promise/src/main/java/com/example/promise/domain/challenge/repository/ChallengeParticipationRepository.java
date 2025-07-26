package com.example.promise.domain.challenge.repository;

import com.example.promise.domain.challenge.entity.ChallengeGroup;
import com.example.promise.domain.challenge.entity.ChallengeParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChallengeParticipationRepository extends JpaRepository<ChallengeParticipation, Long> {
    boolean existsByUserIdAndChallengeGroup(Long userId, ChallengeGroup group);
    List<ChallengeParticipation> findAllByChallengeGroup(ChallengeGroup group);
    long countByChallengeGroupAndIsSuccessTrue(ChallengeGroup group);
    Optional<ChallengeParticipation> findByUserIdAndChallengeGroup(Long userId, ChallengeGroup group);
}
