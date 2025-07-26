package com.example.promise.domain.challenge.controller;

import com.example.promise.domain.challenge.dto.*;
import com.example.promise.domain.challenge.service.ChallengeService;
import com.example.promise.global.auth.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @Operation(summary = "챌린지 그룹 자동 생성", description = "복약 기간과 시작일을 기준으로 챌린지 그룹을 생성합니다.")
    @PostMapping("/groups/auto-create")
    public void createChallengeGroup(@RequestParam int duration,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        challengeService.createChallengeGroupByDuration(duration, startDate);
    }

    @Operation(summary = "참여 가능한 챌린지 그룹 조회", description = "특정 날짜 기준으로 참여 가능한 챌린지 그룹 목록을 조회합니다.")
    @GetMapping("/groups")
    public List<ChallengeResponseDto.ChallengeGroupResponseDto> getAvailableGroups(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return challengeService.getAvailableChallengeGroups(date);
    }

    @Operation(summary = "챌린지 그룹 참여", description = "특정 챌린지 그룹에 사용자가 참여합니다.")
    @PostMapping("/participate")
    public void participateInChallenge(@RequestBody ChallengeRequestDto.ChallengeParticipationRequestDto request, @Parameter(hidden = true) @AuthUser Long userId) {
        challengeService.participateInChallenge(request, userId);
    }

    @Operation(summary = "내 챌린지 참여 현황 조회", description = "현재 로그인한 사용자의 챌린지 참여 정보를 조회합니다.")
    @GetMapping("/mine")
    public List<ChallengeResponseDto.MyChallengeDto> getMyChallenges(@Parameter(hidden = true) @AuthUser Long userId) {
        return challengeService.getMyChallenges(userId);
    }

    @Operation(summary = "일일 복약 성공 기록 등록", description = "해당 챌린지 참여에 대해 하루 복약 성공 여부를 기록합니다.")
    @PostMapping("/record")
    public void recordDailyResult(@RequestBody ChallengeRequestDto.DailyChallengeRecordRequestDto request) {
        challengeService.recordDailyChallenge(request);
    }

    @Operation(summary = "챌린지 포인트 정산", description = "챌린지 종료 후 성공/실패자에 따라 포인트를 정산합니다.")
    @PostMapping("/groups/{id}/settle")
    public void settleChallenge(@PathVariable Long id) {
        challengeService.settleChallenge(id);
    }

    @Operation(summary = "챌린지 그룹별 실시간 랭킹 조회", description = "특정 챌린지 그룹 내에서의 실시간 사용자 랭킹을 조회합니다.")
    @GetMapping("/groups/{id}/ranking")
    public List<ChallengeResponseDto.ChallengeRankingDto> getRanking(@PathVariable Long id,  @Parameter(hidden = true) @AuthUser Long userId) {
        return challengeService.getGroupRanking(id, userId);
    }

    @Operation(summary = "내 전체 챌린지 순위 조회", description = "사용자의 전체 챌린지 누적 포인트 기반 순위를 조회합니다.")
    @GetMapping("/ranking/me")
    public ChallengeResponseDto.ChallengeRankingDto getMyRank(@Parameter(hidden = true) @AuthUser Long userId) {
        return challengeService.getMyTotalRanking(userId);
    }
}