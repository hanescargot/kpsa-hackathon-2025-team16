package com.example.promise.domain.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ChallengeResponseDto {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChallengeGroupResponseDto {
        private Long groupId;
        private String groupName;
        private boolean isLongTerm;
        private int durationDays;
        private LocalDate startDate;
        private LocalDate endDate;
    }


    // 내 챌린지 참여 현황
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyChallengeDto {
        private Long challengeGroupId;
        private String groupName;
        private boolean isLongTerm;
        private int successDays;
        private int totalDays;
        private Long currentPoint;
        private boolean completed;
    }

    // 랭킹 표시용 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChallengeRankingDto {
        private Long userId;
        private String nickname;
        private Long totalPoint;
        private int rank;
        private boolean isMe;
    }

}
