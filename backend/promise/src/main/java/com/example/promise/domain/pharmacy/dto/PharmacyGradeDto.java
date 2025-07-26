package com.example.promise.domain.pharmacy.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyGradeDto {
    private Long pharmacyId;
    private String pharmacyName;
    private String address;
    private double averageSuccessRate;  // 약국 환자 평균 성공률
    private String grade;               // 숲/나무/새싹/없음
    private int participantCount;       // 총 참여자 수
}

