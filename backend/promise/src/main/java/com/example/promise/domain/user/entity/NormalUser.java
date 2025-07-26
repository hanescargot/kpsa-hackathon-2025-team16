package com.example.promise.domain.user.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("USER")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalUser extends BaseUser {
    private LocalDateTime sleepStartTime;
    private LocalDateTime sleepEndTime;

    private String morningTime; // 예: "08:00"
    private String lunchTime;   // 예: "13:00"
    private String eveningTime; // 예: "19:00"

    @Builder.Default
    private Long point = 0L;


}

