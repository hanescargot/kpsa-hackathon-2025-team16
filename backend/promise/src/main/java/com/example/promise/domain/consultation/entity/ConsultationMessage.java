package com.example.promise.domain.consultation.entity;

import com.example.promise.domain.user.entity.status.UserRole;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @Enumerated(EnumType.STRING)
    private UserRole sender; // USER or PHARMACIST

    private String message;

    private LocalDateTime sentAt;
}

