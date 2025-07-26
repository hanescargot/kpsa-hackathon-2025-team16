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

    private boolean isSuccess;  // 복약횟수 충족 여부
    private boolean completed;  // 정산 완료 여부
}

