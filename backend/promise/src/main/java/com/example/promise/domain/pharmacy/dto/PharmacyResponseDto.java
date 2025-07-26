package com.example.promise.domain.pharmacy.dto;

import com.example.promise.domain.pharmacy.entity.status.PharmacyGrade;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyResponseDto {
    private Long pharmacyId;
    private String name;
    private String phone;
    private String address;
    private Double lat;
    private Double lng;
    private String openHours;
    private PharmacyGrade grade;

    public static PharmacyResponseDto fromEntity(com.example.promise.domain.pharmacy.entity.Pharmacy pharmacy) {
        return PharmacyResponseDto.builder()
                .pharmacyId(pharmacy.getPharmacyId())
                .name(pharmacy.getName())
                .phone(pharmacy.getPhone())
                .address(pharmacy.getAddress())
                .lat(pharmacy.getLat())
                .lng(pharmacy.getLng())
                .openHours(pharmacy.getOpenHours())
                .grade(pharmacy.getGrade())
                .build();
    }
}
