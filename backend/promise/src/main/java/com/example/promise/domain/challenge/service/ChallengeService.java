package com.example.promise.domain.challenge.service;

import com.example.promise.domain.challenge.dto.ChallengeResponseDto;
import com.example.promise.domain.challenge.entity.ChallengeGroup;
import com.example.promise.domain.challenge.entity.ChallengeParticipation;
import com.example.promise.domain.challenge.repository.ChallengeGroupRepository;
import com.example.promise.domain.challenge.repository.ChallengeParticipationRepository;
import com.example.promise.domain.medicationschedule.entity.MedicationSlot;
import com.example.promise.domain.medicationschedule.repository.MedicationSlotRepository;
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

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeGroupRepository challengeGroupRepository;
    private final ChallengeParticipationRepository participationRepository;
    private final NormalUserRepository userRepository;
    private final NotificationService notificationService;
    private final MedicationSlotRepository medicationSlotRepository;

    // ✅ 챌린지 그룹은 날짜 기준으로 단일 생성
    public ChallengeGroup findOrCreateGroup(LocalDate date) {
        return challengeGroupRepository.findByChallengeDate(date)
                .orElseGet(() -> {
                    ChallengeGroup group = ChallengeGroup.builder()
                            .challengeDate(date)
                            .totalPoint(0L)
                            .build();
                    return challengeGroupRepository.save(group);
                });
    }

    public void autoParticipateIfNeeded(Long userId, LocalDate date, int doseCount) {
        ChallengeGroup group = challengeGroupRepository.findByChallengeDate(date)
                .orElseGet(() -> challengeGroupRepository.save(
                        ChallengeGroup.builder()
                                .challengeDate(date)
                                .totalPoint(0L)
                                .isSettled(false)
                                .build()
                ));

        boolean already = participationRepository.existsByUserIdAndChallengeGroup(userId, group);
        if (already) return;

        NormalUser user = userRepository.findById(userId).orElseThrow();

        ChallengeParticipation participation = ChallengeParticipation.builder()
                .user(user)
                .challengeGroup(group)
                .requiredDoseCount(doseCount)
                .takenCount(0)
                .isSuccess(false)
                .completed(false)
                .build();

        group.setTotalPoint(group.getTotalPoint() + 100);
        participationRepository.save(participation);
    }


    // ✅ 참여: 유저 기준 복약 횟수 저장 + 배팅
    @Transactional
    public void participate(Long userId, LocalDate date, int doseCount) {
        ChallengeGroup group = findOrCreateGroup(date);
        if (participationRepository.existsByUserIdAndChallengeGroup(userId, group)) return;

        NormalUser user = userRepository.findById(userId).orElseThrow();

        ChallengeParticipation participation = ChallengeParticipation.builder()
                .user(user)
                .challengeGroup(group)
                .requiredDoseCount(doseCount)
                .takenCount(0)
                .isSuccess(false)
                .completed(false)
                .build();

        group.setTotalPoint(group.getTotalPoint() + 100); // 🔸 배팅
        participationRepository.save(participation);
    }

    // ✅ 복약 이행 기록
    @Transactional
    public void recordDailyChallenge(Long participationId, int takenCount) {
        ChallengeParticipation p = participationRepository.findById(participationId).orElseThrow();
        if (p.isCompleted()) return;

        p.setTakenCount(takenCount);
        if (takenCount >= p.getRequiredDoseCount()) {
            p.setSuccess(true);
        }
    }

    // ✅ 챌린지 정산 (100% 성공자만 분배)
    @Transactional
    public void settle(LocalDate date) {
        ChallengeGroup group = challengeGroupRepository.findByChallengeDate(date)
                .orElseThrow();

        if (group.isSettled()) return;

        List<ChallengeParticipation> all = participationRepository.findAllByChallengeGroup(group);
        List<ChallengeParticipation> success = all.stream()
                .filter(p -> p.getTakenCount() >= p.getRequiredDoseCount())
                .toList();

        long perPersonReward = success.isEmpty() ? 0 : group.getTotalPoint() / success.size();

        for (ChallengeParticipation p : all) {
            NormalUser user = p.getUser();

            if (p.getTakenCount() >= p.getRequiredDoseCount()) {
                user.setPoint(user.getPoint() + perPersonReward);
                notificationService.sendNotification(user, "챌린지에 성공하여 " + perPersonReward + "포인트를 받았습니다.");
                p.setSuccess(true);
            } else {
                notificationService.sendNotification(user, "오늘 챌린지에 실패하여 포인트를 받지 못했습니다.");
            }

            p.setCompleted(true);
        }

        group.setTotalPoint(0L);
        group.setSettled(true);
    }

    // ✅ 전날 챌린지 자동 정산
    @Scheduled(cron = "0 5 0 * * *")
    public void autoSettleDailyChallenges() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        challengeGroupRepository.findByChallengeDate(yesterday)
                .ifPresent(group -> settle(group.getChallengeDate()));
    }

    public ChallengeResponseDto.MyChallengeSummary getMyYesterdayChallengeSummary(Long userId) {
        LocalDate date = LocalDate.now().minusDays(1);

        ChallengeGroup group = challengeGroupRepository.findByChallengeDate(date)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHALLENGE_NOT_FOUND));

        ChallengeParticipation participation = participationRepository
                .findByUserIdAndChallengeGroup(userId, group)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHALLENGE_NOT_FOUND));

        boolean success = participation.isSuccess();
        long receivedPoint = success && group.isSettled()
                ? group.getTotalPoint() / participationRepository.countByChallengeGroupAndIsSuccessTrue(group)
                : 0;

        NormalUser user = participation.getUser();

        return ChallengeResponseDto.MyChallengeSummary.builder()
                .challengeDate(group.getChallengeDate())
                .requiredDoseCount(participation.getRequiredDoseCount())
                .takenCount(participation.getTakenCount())
                .success(success)
                .receivedPoint(receivedPoint)
                .currentTotalPoint(user.getPoint())
                .build();
    }

    private String maskName(String name) {
        if (name.length() <= 1) return "*";
        if (name.length() == 2) return name.charAt(0) + "*";
        return name.charAt(0) + "o" + name.charAt(name.length() - 1);
    }

    @Transactional
    public ChallengeResponseDto.TodayChallengeInfo getTodayChallengeInfo(LocalDate date) {
        ChallengeGroup group = challengeGroupRepository.findByChallengeDate(date)
                .orElseThrow(() -> new RuntimeException("챌린지 그룹이 존재하지 않음: " + date));

        List<ChallengeParticipation> participants = participationRepository.findAllByChallengeGroup(group);

        List<ChallengeResponseDto.ChallengeParticipantDto> participantDtos = participants.stream().map(p -> {
            NormalUser user = p.getUser();
            int requiredDoseCount = p.getRequiredDoseCount();

            // ✅ 해당 날짜의 슬롯 중 taken == true인 개수
            List<MedicationSlot> slots = medicationSlotRepository.findByUserAndDate(user, date);
            int takenCount = (int) slots.stream().filter(s -> Boolean.TRUE.equals(s.getTaken())).count();

            double successRate = requiredDoseCount == 0 ? 0.0 : ((double) takenCount / requiredDoseCount) * 100.0;

            return ChallengeResponseDto.ChallengeParticipantDto.builder()
                    .maskedName(maskName(user.getName()))
                    .requiredDoseCount(requiredDoseCount)
                    .takenCount(takenCount)
                    .successRate(successRate)
                    .build();
        }).toList();

        int totalPoint = participants.size() * 100;

        return ChallengeResponseDto.TodayChallengeInfo.builder()
                .challengeDate(date)
                .totalPoint(totalPoint)
                .participants(participantDtos)
                .build();
    }


    @Transactional
    public void updateChallengeProgress(Long userId, LocalDate date) {
        // 1. 챌린지 그룹 찾기
        challengeGroupRepository.findByChallengeDate(date).ifPresent(group -> {
            // 2. 챌린지 참여 내역 찾기
            participationRepository.findByUserIdAndChallengeGroup(userId, group).ifPresent(p -> {
                if (p.isCompleted()) return;

                // 3. 해당 날짜의 복약 슬롯 중 taken 된 것 카운트
                List<MedicationSlot> slots = medicationSlotRepository.findByUserAndDate(p.getUser(), date);
                int takenCount = (int) slots.stream().filter(s -> Boolean.TRUE.equals(s.getTaken())).count();

                // 4. 챌린지 기록 갱신
                p.setTakenCount(takenCount);
                p.setSuccess(takenCount >= p.getRequiredDoseCount());
            });
        });
    }




}
