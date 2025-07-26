package com.example.promise.domain.user.entity;


import com.example.promise.domain.challenge.entity.Challenge;
import com.example.promise.domain.consultation.entity.Consultation;
import com.example.promise.domain.guardian.entity.Guardian;
import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.prescription.entity.Prescription;
import com.example.promise.domain.user.entity.status.Activity;
import com.example.promise.domain.user.entity.status.MealPattern;
import com.example.promise.domain.user.entity.status.UserRole;
import com.example.promise.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String kakaoId;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, length = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private MealPattern mealPattern;

    @Column
    private Float sleepHours;

    private Long point;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Activity activity= Activity.ACTIVITY;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pharmacist pharmacist;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guardian> guardians;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Challenge> challenges;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.example.promise.domain.medication.entity.MedicationSchedule> medicationSchedules;

}