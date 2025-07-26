package com.example.promise.domain.pharmacy.controller;

import com.example.promise.domain.pharmacy.dto.PharmacyGradeDto;
import com.example.promise.domain.pharmacy.service.PharmacyApiService;
import com.example.promise.domain.pharmacy.service.PharmacyGradeService;
import com.example.promise.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyGradeService pharmacyGradeService;
    private final PharmacyApiService pharmacyApiService;

    @GetMapping("/grades")
    public ApiResponse<Map<String, List<PharmacyGradeDto>>> getPharmacyGrades() {
        return ApiResponse.onSuccess(pharmacyGradeService.calculatePharmacyGradesByRegion());
    }

    @PostMapping("/sync")
    public ApiResponse<String> syncPharmacy(@RequestParam String name) {
        pharmacyApiService.fetchAndSavePharmacyList(name);
        return ApiResponse.onSuccess("약국 정보 동기화 완료");
    }
}

