package com.example.promise.domain.user.converter;

import com.example.promise.domain.user.dto.AuthRequestDTO;
import com.example.promise.domain.user.dto.AuthResponseDTO;
import com.example.promise.domain.user.entity.User;
import com.example.promise.domain.user.entity.status.Activity;
import com.example.promise.domain.user.entity.status.UserRole;
import com.example.promise.domain.user.entity.status.Gender;

public class AuthConverter {

    public static User toUser(AuthRequestDTO.SignRequestDTO request, UserRole role) {

        return User.builder()
                .activity(Activity.ACTIVITY)
                .email(request.getEmail())
                .birthDate(request.getBirthDate())
                .name(request.getName())
                .password(request.getPassword())
                .phone(request.getPhone())
                .sleepStartTime(request.getSleepStartTime())
                .sleepEndTime(request.getSleepEndTime())
                .mealTimes(request.getMealTimes())
                .gender(request.getGender())
                .role(role)
                .build();

    }

    public static AuthResponseDTO.SignResponseDTO toSigninResponseDTO(User user){
        return AuthResponseDTO.SignResponseDTO.builder()
                .name(user.getName())
                .role(user.getRole())
                .id(user.getId())
                .build();

    }
}
