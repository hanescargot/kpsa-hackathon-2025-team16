package com.example.promise.domain.challenge.repository;

import com.example.promise.domain.challenge.entity.ChallengeGroup;
import com.example.promise.domain.challenge.entity.ChallengeParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeParticipationRepository extends JpaRepository<ChallengeParticipation, Long> {
    List<ChallengeParticipation> findAllByUserId(Long userId);
    List<ChallengeParticipation> findAllByChallengeGroup(ChallengeGroup group);
}

