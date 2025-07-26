package com.example.promise.domain.prescription.entity;

import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.pharmacy.entity.Pharmacy;
import com.example.promise.domain.user.entity.NormalUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Prescription {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private NormalUser user;

    @ManyToOne
    @JoinColumn(name = "pharmacist_id")
    private Pharmacist pharmacist;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;


    private LocalDate prescribedAt;// 조제일자
    private Boolean viaOcr;
    private Boolean isVerified;

    private String hospitalName;
    private String doctorName;
    private String patientName;

}
