package com.example.promise.domain.challenge.entity;

import com.example.promise.domain.user.entity.NormalUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ChallengeParticipation {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private NormalUser user;

    @ManyToOne
    private ChallengeGroup challengeGroup;

    private int requiredDoseCount; // 사용자 복약 기준 (1~3)

    private int takenCount; // 실제 복약한 횟수

    private boolean isSuccess;

    private boolean completed;
}

