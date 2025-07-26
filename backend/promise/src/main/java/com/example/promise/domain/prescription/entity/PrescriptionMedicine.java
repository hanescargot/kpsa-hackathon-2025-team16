package com.example.promise.domain.prescription.entity;

import com.example.promise.domain.medicine.entity.Medicine;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preid;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private String does;

    private int timesPerDay;

    private int periodDays;

    @Enumerated(EnumType.STRING)
    private TimeSlot timeSlot;

    public enum TimeSlot {
        MORNING, NOON, EVENING, NIGHT
    }
}
