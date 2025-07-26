package com.example.promise.domain.user.converter;

import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.pharmacy.entity.Pharmacy;
import com.example.promise.domain.user.dto.AuthRequestDTO;
import com.example.promise.domain.user.dto.AuthResponseDTO;
import com.example.promise.domain.user.entity.BaseUser;
import com.example.promise.domain.user.entity.NormalUser;
import com.example.promise.domain.user.entity.status.Activity;
import com.example.promise.domain.user.entity.status.UserRole;

public class AuthConverter {

    public static NormalUser toUser(AuthRequestDTO.UserSignupDTO dto) {
        return NormalUser.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .birthDate(dto.getBirthDate())
                .phone(dto.getPhone())
                .sleepStartTime(dto.getSleepStartTime())
                .sleepEndTime(dto.getSleepEndTime())
                .mealTimes(dto.getMealTimes())
                .gender(dto.getGender())
                .activity(Activity.ACTIVITY)
                .role(UserRole.USER)
                .build();
    }

    public static Pharmacist toPharmacist(AuthRequestDTO.PharmacistSignupDTO dto) {

        return Pharmacist.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .phone(dto.getPhone())
                .role(UserRole.PHARMACIST)
                .pharmacyVerify(dto.getPharmacyVerify())
                .build();
    }

    public static AuthResponseDTO.SignResponseDTO toSigninResponseDTO(BaseUser user){
        return AuthResponseDTO.SignResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .id(user.getId())
                .build();
    }

}
