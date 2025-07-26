// 6. ChallengeController.java
package com.example.promise.domain.challenge.controller;

import com.example.promise.domain.challenge.dto.ChallengeResponseDto;
import com.example.promise.domain.challenge.service.ChallengeService;
import com.example.promise.global.auth.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    /**
     * ✅ 챌린지 참여 (100포인트 배팅, doseCount는 유저 기준)
     */
    @Operation(summary = "챌린지 참여", description = "특정 날짜(date)에 복약 횟수(doseCount)를 입력하여 챌린지에 참여합니다. 1인당 100포인트 자동 배팅됩니다.")
    @PostMapping("/participate")
    public void participate(@RequestParam LocalDate date,
                            @RequestParam int doseCount,
                            @Parameter(hidden = true) @AuthUser Long userId) {
        challengeService.participate(userId, date, doseCount);
    }

    /**
     * ✅ 복약 성공 기록 (참여 ID 기준, takenCount 저장)
     */
    @Operation(summary = "복약 성공 기록", description = "참여 ID(participationId)에 대해 실제 복약 횟수(takenCount)를 저장합니다. 기준 이상이면 성공 처리됩니다.")
    @PostMapping("/record")
    public void record(@RequestParam Long participationId,
                       @RequestParam int takenCount) {
        challengeService.recordDailyChallenge(participationId, takenCount);
    }

    /**
     * ✅ 챌린지 수동 정산 (관리자/테스트용)
     */
    @Operation(summary = "챌린지 정산", description = "특정 날짜(date)의 챌린지를 정산합니다. 성공자들에게 포인트 분배가 이루어집니다.")
    @PostMapping("/settle")
    public void settle(@RequestParam LocalDate date) {
        challengeService.settle(date);
    }

    /**
     * ✅ 내 챌린지 정산 결과 확인
     */
    @GetMapping("/summary/yesterday")
    @Operation(summary = "어제 챌린지 요약 조회", description = "어제 챌린지 결과(복약 성공 여부, 받은 포인트, 누적 포인트)를 조회합니다.")
    public ChallengeResponseDto.MyChallengeSummary getYesterdaySummary(@Parameter(hidden = true) @AuthUser Long userId) {
        return challengeService.getMyYesterdayChallengeSummary(userId);
    }


    @GetMapping("/today")
    @Operation(summary = "오늘 챌린지 참여 현황", description = "오늘 챌린지에 참여한 사용자 목록과 복약 성공률을 반환합니다.")
    public ChallengeResponseDto.TodayChallengeInfo getTodayStatus() {
        return challengeService.getTodayChallengeInfo(LocalDate.now());
    }

}
