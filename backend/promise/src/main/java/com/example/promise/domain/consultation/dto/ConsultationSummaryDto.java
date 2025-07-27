package com.example.promise.domain.consultation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ConsultationSummaryDto {
    private Long consultationId;
    private String userName;
    private String latestMessage;
    private LocalDateTime sentAt;
}
