package com.example.promise.domain.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private String name;
    private String phone;
    private String email;
    private Long point;
    private String guardianPhone;
}
