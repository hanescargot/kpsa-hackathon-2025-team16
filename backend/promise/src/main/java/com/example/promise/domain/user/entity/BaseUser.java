package com.example.promise.domain.user.entity;

import com.example.promise.domain.user.entity.status.Activity;
import com.example.promise.domain.user.entity.status.Gender;
import com.example.promise.domain.user.entity.status.UserRole;
import com.example.promise.global.common.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseUser extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;
    private String name;
    private LocalDate birthDate;
    private String phone;
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private Activity activity;
}

