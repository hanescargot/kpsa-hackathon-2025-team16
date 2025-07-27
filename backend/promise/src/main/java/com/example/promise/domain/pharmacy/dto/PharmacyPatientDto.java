package com.example.promise.domain.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class PharmacyPatientDto {
    private Long userId;
    private String name;
    private LocalDate prescribedAt;
}
