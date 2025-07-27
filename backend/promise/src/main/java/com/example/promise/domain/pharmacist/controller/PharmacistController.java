package com.example.promise.domain.pharmacist.controller;

import com.example.promise.domain.pharmacist.dto.PharmacistResponseDTO;
import com.example.promise.domain.pharmacist.service.PharmacistService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacists")
@RequiredArgsConstructor
public class PharmacistController {

    private final PharmacistService pharmacistService;

    @GetMapping
    @Operation(summary = "약사 전체 조회", description = "모든 약사 정보를 조회합니다.")
    public List<PharmacistResponseDTO> getAllPharmacists() {
        return pharmacistService.getAllPharmacists();
    }
}
