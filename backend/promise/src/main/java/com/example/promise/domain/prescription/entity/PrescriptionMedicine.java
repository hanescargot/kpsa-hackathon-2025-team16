package com.example.promise.domain.prescription.entity;

import com.example.promise.domain.medicine.entity.Medicine;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "prescription_medicine")
public class PrescriptionMedicine {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @Column(columnDefinition = "TEXT")
    private String caution;// 주의사항
    @Column(columnDefinition = "TEXT")
    private String usageDescription;  //복약 안내
    @Column(columnDefinition = "TEXT")
    private String effect;       // 효능
}
