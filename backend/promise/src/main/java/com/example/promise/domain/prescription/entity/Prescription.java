package com.example.promise.domain.prescription.entity;

import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pharmacist_id")
    private Pharmacist pharmacist;

    private Date prescribedAt;

    private Boolean viaOcr;

    private Boolean isVerified;
}
