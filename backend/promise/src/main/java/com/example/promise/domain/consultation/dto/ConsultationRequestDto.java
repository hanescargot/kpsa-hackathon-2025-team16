package com.example.promise.domain.consultation.dto;

import lombok.Getter;

@Getter
public class ConsultationRequestDto {
    private Long pharmacistId;
    private String message; // 첫 메시지
}
