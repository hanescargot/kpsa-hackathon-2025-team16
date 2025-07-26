package com.example.promise.domain.challenge.service;

import com.example.promise.domain.challenge.dto.ChallengeResponseDto;
import com.example.promise.domain.challenge.entity.*;
import com.example.promise.domain.challenge.repository.*;
import com.example.promise.domain.notification.service.NotificationService;
import com.example.promise.domain.user.entity.NormalUser;
import com.example.promise.domain.user.repository.NormalUserRepository;
import com.example.promise.global.code.status.ErrorStatus;
import com.example.promise.global.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeGroupRepository challengeGroupRepository;
    private final ChallengeParticipationRepository participationRepository;
    private final NormalUserRepository userRepository;
    private final NotificationService notificationService;

    public ChallengeGroup findOrCreateGroup(LocalDate date, int doseCount) {
        return challengeGroupRepository.findByChallengeDateAndRequiredDoseCount(date, doseCount)
                .orElseGet(() -> {
                    ChallengeGroup group = ChallengeGroup.builder()
                            .challengeDate(date)
                            .requiredDoseCount(doseCount)
                            .totalPoint(0L)
                            .build();
                    return challengeGroupRepository.save(group);
                });
    }

    @Transactional
    public void participate(Long userId, LocalDate date, int doseCount) {
        ChallengeGroup group = findOrCreateGroup(date, doseCount);
        if (participationRepository.existsByUserIdAndChallengeGroup(userId, group)) return;

        NormalUser user = userRepository.findById(userId).orElseThrow();

        ChallengeParticipation participation = ChallengeParticipation.builder()
                .user(user)
                .challengeGroup(group)
                .isSuccess(false)
                .completed(false)
                .build();

        group.setTotalPoint(group.getTotalPoint() + 100);
        participationRepository.save(participation);
    }

    @Transactional
    public void recordDailyChallenge(Long participationId, int takenCount) {
        ChallengeParticipation p = participationRepository.findById(participationId).orElseThrow();
        if (p.isSuccess() || p.isCompleted()) return;
        int required = p.getChallengeGroup().getRequiredDoseCount();
        if (takenCount >= required) {
            p.setSuccess(true);
        }
    }

    @Transactional
    public void settle(LocalDate date, int doseCount) {
        ChallengeGroup group = challengeGroupRepository.findByChallengeDateAndRequiredDoseCount(date, doseCount)
                .orElseThrow();

        if (group.isSettled()) return;

        List<ChallengeParticipation> all = participationRepository.findAllByChallengeGroup(group);
        List<ChallengeParticipation> success = all.stream().filter(ChallengeParticipation::isSuccess).toList();

        long perPersonReward = success.isEmpty() ? 0 : group.getTotalPoint() / success.size();

        for (ChallengeParticipation p : all) {
            NormalUser user = p.getUser();

            if (p.isSuccess()) {
                user.setPoint(user.getPoint() + perPersonReward);
                notificationService.sendNotification(user, "챌린지에 성공하여 " + perPersonReward + "포인트를 받았습니다.");
            } else {
                notificationService.sendNotification(user, "오늘 챌린지에 실패하여 포인트를 받지 못했습니다.");
            }

            p.setCompleted(true);
        }

        // 정산 후 0 point
        group.setTotalPoint(0L);
    }

    @Scheduled(cron = "0 5 0 * * *")
    public void autoSettleDailyChallenges() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<ChallengeGroup> groups = challengeGroupRepository.findAll()
                .stream()
                .filter(g -> g.getChallengeDate().equals(yesterday))
                .toList();

        for (ChallengeGroup group : groups) {
            settle(group.getChallengeDate(), group.getRequiredDoseCount());
        }
    }

    public ChallengeResponseDto.MyChallengeResult getMyChallengeResult(Long userId, LocalDate date, int doseCount) {
        ChallengeGroup group = challengeGroupRepository
                .findByChallengeDateAndRequiredDoseCount(date, doseCount)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHALLENGE_NOT_FOUND));

        ChallengeParticipation participation = participationRepository
                .findByUserIdAndChallengeGroup(userId, group)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHALLENGE_NOT_FOUND));

        boolean success = participation.isSuccess();
        long point = success && group.getTotalPoint() != null
                ? group.getTotalPoint() / (long) participationRepository.countByChallengeGroupAndIsSuccessTrue(group)
                : 0;

        return ChallengeResponseDto.MyChallengeResult.builder()
                .challengeDate(group.getChallengeDate())
                .requiredDoseCount(group.getRequiredDoseCount())
                .success(success)
                .receivedPoint(point)
                .build();
    }

}
