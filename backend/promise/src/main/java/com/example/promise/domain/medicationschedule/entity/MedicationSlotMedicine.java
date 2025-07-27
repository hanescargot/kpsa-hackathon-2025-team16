package com.example.promise.domain.medicationschedule.entity;

import com.example.promise.domain.prescription.entity.PrescriptionMedicine;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationSlotMedicine {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private MedicationSlot slot;

    @ManyToOne(fetch = FetchType.LAZY)
    private PrescriptionMedicine prescriptionMedicine;
}
