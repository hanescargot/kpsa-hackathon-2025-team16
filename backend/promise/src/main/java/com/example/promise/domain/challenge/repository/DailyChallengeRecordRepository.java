package com.example.promise.domain.challenge.repository;

import com.example.promise.domain.challenge.entity.ChallengeParticipation;
import com.example.promise.domain.challenge.entity.DailyChallengeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DailyChallengeRecordRepository extends JpaRepository<DailyChallengeRecord, Long> {
    boolean existsByParticipationAndDate(ChallengeParticipation participation, LocalDate date);
}
