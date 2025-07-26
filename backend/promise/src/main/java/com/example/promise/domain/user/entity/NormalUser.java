package com.example.promise.domain.user.entity;

import com.example.promise.domain.challenge.entity.Challenge;
import com.example.promise.domain.consultation.entity.Consultation;
import com.example.promise.domain.guardian.entity.Guardian;
import com.example.promise.domain.medicationschedule.entity.MedicationSchedule;
import com.example.promise.domain.prescription.entity.Prescription;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("USER")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalUser extends BaseUser {
    private LocalDateTime sleepStartTime;
    private LocalDateTime sleepEndTime;
    private String mealTimes;

    private Long point = 0L;


}

