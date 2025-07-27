package com.example.promise.domain.challenge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyChallengeRecord {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participation_id")
    private ChallengeParticipation participation;

    private LocalDate date;
    private boolean isTaken; // 복약 성공 여부
}
