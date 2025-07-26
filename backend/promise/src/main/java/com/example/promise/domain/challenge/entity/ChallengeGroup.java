package com.example.promise.domain.challenge.entity;

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

    private String groupName; // "3일 챌린지", "2025-07 장기 챌린지" 등
    private Boolean isLongTerm; // true = 장기, false = 단기
    private int durationDays; // 3, 5, 7 or 30 이상

    private LocalDate startDate;
    private LocalDate endDate;

    public boolean isLongTerm() {
        return Boolean.TRUE.equals(this.isLongTerm);
    }
}
