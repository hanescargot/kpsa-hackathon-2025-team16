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
    private String mealTimes;

    private Long point = 0L;

}

