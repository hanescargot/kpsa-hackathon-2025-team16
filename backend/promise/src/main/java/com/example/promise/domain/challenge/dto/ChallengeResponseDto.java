package com.example.promise.domain.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class ChallengeResponseDto {

    @Getter
    @Builder
    public static class MyChallengeSummary {
        private LocalDate challengeDate;
        private int requiredDoseCount;
        private int takenCount;
        private boolean success;
        private long receivedPoint;
        private long currentTotalPoint;
    }

    @Getter
    @Builder
    public static class TodayChallengeUser {
        private String maskedName;
        private int requiredDoseCount;
        private int takenCount;
        private double successRate; // 0.0 ~ 1.0
    }


    @Getter
    @AllArgsConstructor
    @Builder
    public static class TodayChallengeInfo {
        private LocalDate challengeDate;
        private int totalPoint;
        private List<ChallengeParticipantDto> participants;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ChallengeParticipantDto {
        private String maskedName;
        private int requiredDoseCount;
        private int takenCount;
        private double successRate;
    }


}
