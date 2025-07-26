package com.example.promise.domain.pharmacy.controller;

import com.example.promise.domain.pharmacy.dto.PharmacyGradeDto;
import com.example.promise.domain.pharmacy.service.PharmacyGradeService;
import com.example.promise.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyGradeService pharmacyGradeService;

    @GetMapping("/grades")
    public ApiResponse<Map<String, List<PharmacyGradeDto>>> getPharmacyGrades() {
        return ApiResponse.onSuccess(pharmacyGradeService.calculatePharmacyGradesByRegion());
    }
}

