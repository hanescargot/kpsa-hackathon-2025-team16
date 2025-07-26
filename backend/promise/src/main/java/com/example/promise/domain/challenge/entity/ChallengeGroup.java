package com.example.promise.domain.challenge.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ChallengeGroup {
    @Id @GeneratedValue
    private Long id;

    private LocalDate challengeDate; // 날짜 기준 단일 그룹

    private Long totalPoint; // 전체 배팅 포인트

    @Column(nullable = false)
    @Builder.Default
    private boolean isSettled = false;
}
