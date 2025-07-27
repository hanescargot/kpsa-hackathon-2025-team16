package com.example.promise.domain.pharmacist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PharmacistResponseDTO {
    private Long id;
    private String name;
    private String pharmacyName;
}
