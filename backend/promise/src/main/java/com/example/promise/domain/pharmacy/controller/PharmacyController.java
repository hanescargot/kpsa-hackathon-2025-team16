package com.example.promise.domain.pharmacy.controller;

import com.example.promise.domain.pharmacy.dto.PharmacyGradeDto;
import com.example.promise.domain.pharmacy.dto.PharmacyPatientDto;
import com.example.promise.domain.pharmacy.dto.PharmacyResponseDto;
import com.example.promise.domain.pharmacy.service.PharmacyApiService;
import com.example.promise.domain.pharmacy.service.PharmacyGradeService;
import com.example.promise.domain.pharmacy.service.PharmacyQueryService;
import com.example.promise.domain.prescription.entity.Prescription;
import com.example.promise.domain.prescription.repository.PrescriptionRepository;
import com.example.promise.domain.user.entity.NormalUser;
import com.example.promise.global.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pharmacies")
@RequiredArgsConstructor
@Tag(name = "Pharmacy", description = "약국 관련 API")
public class PharmacyController {

    private final PharmacyGradeService pharmacyGradeService;
    private final PharmacyApiService pharmacyApiService;
    private final PharmacyQueryService pharmacyQueryService;
    private final PrescriptionRepository prescriptionRepository;

    @Operation(summary = "읍면동 단위 약국 등급 조회", description = "전월 사용자 복약 성공률을 기반으로 약국의 등급을 계산하고, 읍면동 단위로 그룹화하여 반환합니다.")
    @GetMapping("/grades")
    public ApiResponse<Map<String, List<PharmacyGradeDto>>> getPharmacyGrades() {
        return ApiResponse.onSuccess(pharmacyGradeService.calculatePharmacyGradesByRegion());
    }

    @Operation(summary = "공공데이터 약국 정보 동기화", description = "공공 API를 통해 입력한 이름을 포함하는 약국 정보를 동기화하여 저장합니다.")
    @PostMapping("/sync")
    public ApiResponse<String> syncPharmacy(@RequestParam String name) {
        pharmacyApiService.fetchAndSavePharmacyList(name);
        return ApiResponse.onSuccess("약국 정보 동기화 완료");
    }

    @Operation(summary = "전체 약국 목록 조회", description = "모든 약국 정보를 리스트 형태로 반환합니다.")
    @GetMapping
    public List<PharmacyResponseDto> getAllPharmacies() {
        return pharmacyQueryService.getAllPharmacies();
    }

    @Operation(summary = "약국 상세 조회", description = "약국 ID를 기준으로 해당 약국의 상세 정보를 조회합니다.")
    @GetMapping("/{pharmacyId}")
    public PharmacyResponseDto getPharmacy(@PathVariable Long pharmacyId) {
        return pharmacyQueryService.getPharmacyById(pharmacyId);
    }

    @GetMapping("/{pharmacyId}/patients")
    public ResponseEntity<List<PharmacyPatientDto>> getPatientsByPharmacy(@PathVariable Long pharmacyId) {
        List<Prescription> prescriptions = prescriptionRepository.findByPharmacy_PharmacyId(pharmacyId);

        List<PharmacyPatientDto> result = prescriptions.stream()
                .map(prescription -> {
                    NormalUser user = prescription.getUser();
                    return new PharmacyPatientDto(user.getId(), user.getName(), prescription.getPrescribedAt());
                })
                .distinct()
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
