package com.example.promise.domain.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ChallengeRequestDto {

    // 챌린지 참여 요청
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ChallengeParticipationRequestDto {
        private Long challengeGroupId;
        private Long userId; // 추가
    }


    // 하루 복약 성공 기록 등록
    @Getter
    @NoArgsConstructor
    public static class DailyChallengeRecordRequestDto {
        private Long participationId;
        private LocalDate date;
        private boolean isTaken;
    }

}
