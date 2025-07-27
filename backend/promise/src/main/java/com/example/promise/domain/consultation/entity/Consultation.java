package com.example.promise.domain.consultation.entity;

import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.user.entity.NormalUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private NormalUser user;

    @ManyToOne
    @JoinColumn(name = "pharmacist_id", nullable = false)
    private Pharmacist pharmacist;

    private Boolean isCompleted;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}

