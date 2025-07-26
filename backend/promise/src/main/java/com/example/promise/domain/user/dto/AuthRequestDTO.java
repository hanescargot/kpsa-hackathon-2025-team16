package com.example.promise.domain.user.dto;

import com.example.promise.domain.user.entity.status.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class AuthRequestDTO {

    @Getter @Setter
    public static class UserSignupDTO {
        private String email;
        private String password;
        private String name;
        private LocalDate birthDate;
        private String phone;
        private LocalDateTime sleepStartTime;
        private LocalDateTime sleepEndTime;
        private String morningTime; // "08:00"
        private String lunchTime;   // "13:00"
        private String eveningTime; // "19:00"
        private Gender gender;
    }

    @Getter @Setter
    public static class PharmacistSignupDTO {
        private String email;
        private String password;
        private String name;
        private LocalDate birthDate;
        private String phone;
        private String pharmacyName;
        private Long pharmacyVerify;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequestDTO{
        private String email;
        private String password;
    }


}
