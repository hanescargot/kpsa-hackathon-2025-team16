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

    @Operation(summary = "챌린지 자동 참여",
            description = "특정 날짜(date)와 복약 횟수(doseCount)를 기준으로 해당 사용자(userId)가 챌린지에 참여합니다. " +
                    "챌린지 그룹이 없으면 자동으로 생성되며, 사용자 1명당 100포인트가 자동 배팅됩니다.")
    @PostMapping("/participate")
    public void participate(@RequestParam LocalDate date,
                            @RequestParam int doseCount,
                            @Parameter(hidden = true) @AuthUser Long userId) {
        challengeService.participate(userId, date, doseCount);
    }

    @Operation(summary = "일일 복약 성공 기록",
            description = "해당 챌린지 참여(participationId)에 대해 복약 성공 여부를 기록합니다. takenCount가 복약 횟수 이상일 경우 성공 처리됩니다.")
    @PostMapping("/record")
    public void record(@RequestParam Long participationId,
                       @RequestParam int takenCount) {
        challengeService.recordDailyChallenge(participationId, takenCount);
    }

    @Operation(summary = "챌린지 포인트 정산",
            description = "특정 날짜(date)와 복약 횟수(doseCount)를 기준으로 해당 챌린지 그룹의 성공자들을 계산하고 포인트를 정산합니다. 성공자는 그룹의 totalPoint를 나누어 갖습니다.")
    @PostMapping("/settle")
    public void settle(@RequestParam LocalDate date,
                       @RequestParam int doseCount) {
        challengeService.settle(date, doseCount);
    }

    @Operation(summary = "내 챌린지 정산 결과 조회",
            description = "지정된 날짜(date)와 복약 횟수(doseCount)에 대해 내가 성공했는지, 포인트를 얼마나 받았는지 조회합니다.")
    @GetMapping("/result")
    public ChallengeResponseDto.MyChallengeResult getResult(@RequestParam LocalDate date,
                                                            @RequestParam int doseCount,
                                                            @Parameter(hidden = true) @AuthUser Long userId) {
        return challengeService.getMyChallengeResult(userId, date, doseCount);
    }


}
