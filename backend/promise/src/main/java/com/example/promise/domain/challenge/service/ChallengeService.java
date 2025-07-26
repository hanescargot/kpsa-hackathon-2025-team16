package com.example.promise.domain.challenge.service;

import com.example.promise.domain.challenge.dto.*;
import com.example.promise.domain.challenge.entity.*;
import com.example.promise.domain.challenge.repository.*;
import com.example.promise.domain.user.entity.NormalUser;
import com.example.promise.domain.user.repository.NormalUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeGroupRepository challengeGroupRepository;
    private final ChallengeParticipationRepository participationRepository;
    private final DailyChallengeRecordRepository recordRepository;
    private final NormalUserRepository userRepository;

    // 챌린지 그룹 생성
    public void createChallengeGroupByDuration(int duration, LocalDate startDate) {
        boolean isLongTerm = duration >= 30;

        if (!isLongTerm) {
            // 단기 그룹: startDate + duration 조합으로 그룹 중복 방지
            boolean exists = challengeGroupRepository
                    .findByDurationDaysAndStartDate(duration, startDate)
                    .isPresent();

            if (exists) return;
        } else {
            // 장기 그룹: 월 단위로 그룹 중복 방지
            LocalDate firstDayOfMonth = LocalDate.of(startDate.getYear(), startDate.getMonth(), 1);

            boolean exists = challengeGroupRepository
                    .findByIsLongTermTrueAndStartDate(firstDayOfMonth)
                    .isPresent();

            if (exists) return;

            // 시작일을 월 시작일로 고정
            startDate = firstDayOfMonth;
            duration = startDate.lengthOfMonth();
        }

        String groupName = isLongTerm
                ? startDate.getYear() + "-" + startDate.getMonthValue() + " 장기 챌린지"
                : duration + "일 챌린지";

        ChallengeGroup group = ChallengeGroup.builder()
                .groupName(groupName)
                .isLongTerm(isLongTerm)
                .durationDays(duration)
                .startDate(startDate)
                .endDate(startDate.plusDays(duration - 1))
                .build();

        challengeGroupRepository.save(group);
    }



    // 날짜 기준 그룹 조회
    public List<ChallengeResponseDto.ChallengeGroupResponseDto> getAvailableChallengeGroups(LocalDate date) {
        return challengeGroupRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(date, date)
                .stream()
                .map(g -> ChallengeResponseDto.ChallengeGroupResponseDto.builder()
                        .groupId(g.getId())
                        .groupName(g.getGroupName())
                        .isLongTerm(g.isLongTerm())
                        .durationDays(g.getDurationDays())
                        .startDate(g.getStartDate())
                        .endDate(g.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }

    // 챌린지 참여
    @Transactional
    public void participateInChallenge(ChallengeRequestDto.ChallengeParticipationRequestDto request, Long userId) {
        if(userId == null)
            userId = request.getUserId();

        boolean alreadyExists = participationRepository.existsByUserIdAndChallengeGroupId(userId, request.getChallengeGroupId());
        if (alreadyExists)
            return; // 🔒 중복 참여 방지

        NormalUser user = userRepository.findById(userId).orElse(null);
        ChallengeGroup group = challengeGroupRepository.findById(request.getChallengeGroupId()).orElseThrow();

        ChallengeParticipation participation = ChallengeParticipation.builder()
                .user(user)
                .challengeGroup(group)
                .currentPoint(0L)
                .successDays(0)
                .completed(false)
                .build();

        participationRepository.save(participation);
    }

    // 내 챌린지 현황 조회
    public List<ChallengeResponseDto.MyChallengeDto> getMyChallenges(Long userId) {
        return participationRepository.findAllByUserId(userId).stream()
                .map(p -> {
                    ChallengeGroup g = p.getChallengeGroup();
                    int totalDays = Period.between(g.getStartDate(), g.getEndDate()).getDays() + 1;
                    return ChallengeResponseDto.MyChallengeDto.builder()
                            .challengeGroupId(g.getId())
                            .groupName(g.getGroupName())
                            .isLongTerm(g.isLongTerm())
                            .successDays(p.getSuccessDays())
                            .totalDays(totalDays)
                            .currentPoint(p.getCurrentPoint())
                            .completed(p.isCompleted())
                            .build();
                })
                .collect(Collectors.toList());
    }

    // 하루 복약 기록
    @Transactional
    public void recordDailyChallenge(ChallengeRequestDto.DailyChallengeRecordRequestDto request) {
        ChallengeParticipation p = participationRepository.findById(request.getParticipationId()).orElseThrow();

        if (recordRepository.existsByParticipationAndDate(p, request.getDate())) return;

        if (request.isTaken()) {
            p.setSuccessDays(p.getSuccessDays() + 1);
        }

        DailyChallengeRecord record = DailyChallengeRecord.builder()
                .participation(p)
                .date(request.getDate())
                .isTaken(request.isTaken())
                .build();

        recordRepository.save(record);
    }

    // 정산
    @Transactional
    public void settleChallenge(Long groupId) {
        ChallengeGroup group = challengeGroupRepository.findById(groupId).orElseThrow();
        List<ChallengeParticipation> participants = participationRepository.findAllByChallengeGroup(group);

        int totalDays = Period.between(group.getStartDate(), group.getEndDate()).getDays() + 1;
        List<ChallengeParticipation> successUsers = new ArrayList<>();
        List<ChallengeParticipation> failedUsers = new ArrayList<>();

        for (ChallengeParticipation p : participants) {
            double successRate = (double) p.getSuccessDays() / totalDays;
            if ((group.isLongTerm() && successRate >= 0.8) || (!group.isLongTerm() && successRate == 1.0)) {
                successUsers.add(p);
            } else {
                failedUsers.add(p);
            }
            p.setCompleted(true);
        }

        long penalty = 100L;
        long reward = 500L;
        long bonus = successUsers.isEmpty() ? 0 : (failedUsers.size() * penalty) / successUsers.size();

        successUsers.forEach(p -> p.setCurrentPoint(p.getCurrentPoint() + reward + bonus));
        failedUsers.forEach(p -> p.setCurrentPoint(p.getCurrentPoint() - penalty));
    }

    // 그룹 내 랭킹
    public List<ChallengeResponseDto.ChallengeRankingDto> getGroupRanking(Long groupId, Long currentUserId) { // 현재 사용자 ID 파라미터 추가
        ChallengeGroup group = challengeGroupRepository.findById(groupId).orElseThrow();
        List<ChallengeParticipation> list = participationRepository.findAllByChallengeGroup(group);

        // 포인트 기준으로 내림차순 정렬
        List<ChallengeParticipation> sortedList = list.stream()
                .sorted((a, b) -> Long.compare(b.getCurrentPoint(), a.getCurrentPoint()))
                .toList();

        List<ChallengeResponseDto.ChallengeRankingDto> rankingDtos = new ArrayList<>();
        int rank = 1;
        for (ChallengeParticipation p : sortedList) {
            rankingDtos.add(ChallengeResponseDto.ChallengeRankingDto.builder()
                    .userId(p.getUser().getId())
                    .nickname(p.getUser().getName()) // 익명 표시 고려 필요
                    .totalPoint(p.getCurrentPoint())
                    .rank(rank++) // 순위 계산하여 설정
                    .isMe(p.getUser().getId().equals(currentUserId)) // 현재 사용자인지 확인
                    .build());
        }
        return rankingDtos;
    }
    // 내 전체 순위
    public ChallengeResponseDto.ChallengeRankingDto getMyTotalRanking(Long userId) {
        List<ChallengeParticipation> all = participationRepository.findAll();

        Map<Long, Long> userPointMap = new HashMap<>();
        for (ChallengeParticipation p : all) {
            userPointMap.put(p.getUser().getId(), userPointMap.getOrDefault(p.getUser().getId(), 0L) + p.getCurrentPoint());
        }

        List<Map.Entry<Long, Long>> sorted = userPointMap.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .toList();

        int rank = 1;
        for (Map.Entry<Long, Long> e : sorted) {
            if (e.getKey().equals(userId)) {
                NormalUser user = userRepository.findById(userId).orElseThrow();
                return ChallengeResponseDto.ChallengeRankingDto.builder()
                        .userId(userId)
                        .nickname(user.getName())
                        .totalPoint(e.getValue())
                        .rank(rank)
                        .isMe(true)
                        .build();
            }
            rank++;
        }
        throw new RuntimeException("사용자 랭킹 없음");
    }

}
