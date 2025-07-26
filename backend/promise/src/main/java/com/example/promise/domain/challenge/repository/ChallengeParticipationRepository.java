package com.example.promise.domain.challenge.repository;

import com.example.promise.domain.challenge.entity.ChallengeGroup;
import com.example.promise.domain.challenge.entity.ChallengeParticipation;
import com.example.promise.domain.user.entity.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChallengeParticipationRepository extends JpaRepository<ChallengeParticipation, Long> {
    boolean existsByUserIdAndChallengeGroup(Long userId, ChallengeGroup group);
    List<ChallengeParticipation> findAllByChallengeGroup(ChallengeGroup group);
    long countByChallengeGroupAndIsSuccessTrue(ChallengeGroup group);
    Optional<ChallengeParticipation> findByUserIdAndChallengeGroup(Long userId, ChallengeGroup group);
    List<ChallengeParticipation> findAllByUser(NormalUser user);

//    // 🔹 추가: 사용자 ID와 기간 조건으로 참여 기록 조회
//    List<ChallengeParticipation> findAllByUserIdAndChallengeGroup_StartDateLessThanEqualAndChallengeGroup_EndDateGreaterThanEqual(
//            Long userId, LocalDate endDate, LocalDate startDate
//    );
}
