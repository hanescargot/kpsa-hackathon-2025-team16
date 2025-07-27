package com.example.promise.domain.mypage.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserUpdateRequestDto {
    private String name;
    private String phone;
    private LocalDateTime sleepStartTime;
    private LocalDateTime sleepEndTime;
    private String pharmacyName; // 약사 전용 수정 필드
}