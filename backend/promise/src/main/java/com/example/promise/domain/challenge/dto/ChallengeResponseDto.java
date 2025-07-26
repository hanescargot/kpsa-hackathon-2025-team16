package com.example.promise.domain.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ChallengeResponseDto {

    @Getter
    @Builder
    public static class MyChallengeResult {
        private LocalDate challengeDate;
        private int requiredDoseCount;
        private boolean success;
        private long receivedPoint;
    }

}
