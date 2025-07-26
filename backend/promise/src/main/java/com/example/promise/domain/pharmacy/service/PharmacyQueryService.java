package com.example.promise.domain.pharmacy.service;

import com.example.promise.domain.pharmacy.dto.PharmacyResponseDto;
import com.example.promise.domain.pharmacy.entity.Pharmacy;
import com.example.promise.domain.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyQueryService {

    private final PharmacyRepository pharmacyRepository;

    public List<PharmacyResponseDto> getAllPharmacies() {
        return pharmacyRepository.findAll().stream()
                .map(PharmacyResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public PharmacyResponseDto getPharmacyById(Long pharmacyId) {
        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 약국이 존재하지 않습니다. id = " + pharmacyId));
        return PharmacyResponseDto.fromEntity(pharmacy);
    }
}
