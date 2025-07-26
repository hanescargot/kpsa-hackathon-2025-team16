package com.example.promise.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.promise.domain.user.entity.status.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AuthRequestDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignRequestDTO{
        private String email;
        private String name;
        private String password;
        private Gender gender;
        private LocalDate birthDate;
        private String phone;
        private String mealTimes;
        private LocalDateTime sleepStartTime;
        private LocalDateTime sleepEndTime;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginRequestDTO{
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefreshRequestDTO {
        private String refreshToken;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KakaoRequestDTO {
        private String name;
        private Gender gender;
        private LocalDate birthDate;
        private String nickName;
        private String userType;
    }

}
