package com.example.promise.domain.consultation.dto;

import com.example.promise.domain.user.entity.status.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ConsultationMessageResponseDto {
    private UserRole sender;
    private String message;
    private LocalDateTime sentAt;
}