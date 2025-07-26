package com.example.promise.domain.medicationschedule.entity;

import com.example.promise.domain.medicine.entity.Medicine;
import com.example.promise.domain.prescription.entity.PrescriptionMedicine;
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
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private PrescriptionMedicine prescriptionMedicine;

    private LocalDateTime scheduledTime; // 실제 복용 시각
    private Boolean taken;               // 복용 여부
    private LocalDateTime takenAt;       // 실제 복용한 시각

    private Boolean missed;              // 누락 여부
}
