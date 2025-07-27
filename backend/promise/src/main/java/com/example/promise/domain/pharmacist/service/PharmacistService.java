package com.example.promise.domain.pharmacist.service;

import com.example.promise.domain.pharmacist.dto.PharmacistResponseDTO;
import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.pharmacist.repository.PharmacistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacistService {

    private final PharmacistRepository pharmacistRepository;

    public List<PharmacistResponseDTO> getAllPharmacists() {
        return pharmacistRepository.findAll()
                .stream()
                .map(p -> new PharmacistResponseDTO(
                        p.getId(),
                        p.getName(),
                        p.getPharmacy() != null ? p.getPharmacy().getName() : null
                ))
                .collect(Collectors.toList());
    }
}