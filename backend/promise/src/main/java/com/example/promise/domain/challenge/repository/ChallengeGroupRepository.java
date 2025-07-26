package com.example.promise.domain.challenge.repository;

import com.example.promise.domain.challenge.entity.ChallengeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChallengeGroupRepository extends JpaRepository<ChallengeGroup, Long> {
    List<ChallengeGroup> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate start, LocalDate end);
    Optional<ChallengeGroup> findByDurationDaysAndStartDate(int duration, LocalDate startDate);
    Optional<ChallengeGroup> findByIsLongTermTrueAndStartDate(LocalDate startDate);

}