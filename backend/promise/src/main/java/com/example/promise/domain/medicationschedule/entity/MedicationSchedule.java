package com.example.promise.domain.medication.entity;

import com.example.promise.domain.medicine.entity.Medicine;
import com.example.promise.domain.user.entity.NormalUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private NormalUser user;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, TAKEN, SKIPPED
    }
}
