package com.example.promise.domain.user.dto;


import com.example.promise.domain.user.entity.status.UserRole;
import lombok.*;


@Builder
public class AuthResponseDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SignResponseDTO{
        private String email;
        private String name;
        private Long id;
        private UserRole role;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class LoginResponseDTO{
        private String token;
    }


}
