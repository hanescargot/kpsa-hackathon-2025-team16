package com.example.promise.domain.challenge.entity;

import com.example.promise.domain.user.entity.NormalUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeParticipation {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private NormalUser user;

    @ManyToOne
    @JoinColumn(name = "challenge_group_id")
    private ChallengeGroup challengeGroup;

    private Long currentPoint = 0L;

    private int successDays;  // 성공한 날 수 (단기: 3일 중 2일 등)
    private boolean completed; // 챌린지 완료 여부
}

