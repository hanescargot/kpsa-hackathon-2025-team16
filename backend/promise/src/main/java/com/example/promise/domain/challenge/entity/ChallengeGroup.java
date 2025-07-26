package com.example.promise.domain.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeGroup {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate challengeDate; // ex. 2025-07-26
    private int requiredDoseCount;   // ex. 3회 복용 기준

    private Long totalPoint; // 참여자 수 * 100

    @Column(nullable = false)
    @Builder.Default
    private boolean isSettled = false;

}
