package com.example.promise.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class PointHistoryDto {
    private LocalDate challengeDate;
    private int requiredDoseCount;
    private int takenCount;
    private boolean isSuccess;
    private long receivedPoint;
    private long totalPointAfterReward;
}