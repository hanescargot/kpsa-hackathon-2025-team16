package com.example.promise.domain.consultation.entity;

import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.user.entity.NormalUser;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "user_id")
    private NormalUser user;

    @ManyToOne
    @JoinColumn(name = "pharmacist_id")
    private Pharmacist pharmacist;

    private String category;

    private String message;

    private String imageUrl;

    private Boolean isCompleted;
}
