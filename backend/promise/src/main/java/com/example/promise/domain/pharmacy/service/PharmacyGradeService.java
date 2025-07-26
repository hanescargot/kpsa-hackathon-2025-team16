// 7. PharmacyGradeService.java
package com.example.promise.domain.pharmacy.service;

import com.example.promise.domain.challenge.entity.ChallengeGroup;
import com.example.promise.domain.challenge.entity.ChallengeParticipation;
import com.example.promise.domain.challenge.repository.ChallengeParticipationRepository;
import com.example.promise.domain.pharmacy.dto.PharmacyGradeDto;
import com.example.promise.domain.pharmacy.entity.Pharmacy;
import com.example.promise.domain.prescription.entity.Prescription;
import com.example.promise.domain.prescription.repository.PrescriptionRepository;
import com.example.promise.domain.user.entity.NormalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyGradeService {

    private final ChallengeParticipationRepository participationRepository;
    private final PrescriptionRepository prescriptionRepository;

    /**
     * 읍면동 단위로 약국 등급 결과를 그룹화하여 반환
     */
    public Map<String, List<PharmacyGradeDto>> calculatePharmacyGradesByRegion() {
        List<ChallengeParticipation> all = participationRepository.findAll();

        Map<Long, List<Double>> pharmacySuccessRates = new HashMap<>();
        Map<Long, Pharmacy> pharmacyMap = new HashMap<>();

        for (ChallengeParticipation p : all) {
            NormalUser user = p.getUser();
            Optional<Prescription> latestPrescription = prescriptionRepository
                    .findTopByUserOrderByPrescribedAtDesc(user);

            if (latestPrescription.isEmpty()) continue;

            Pharmacy pharmacy = latestPrescription.get().getPharmacy();
            if (pharmacy == null) continue;

            long pharmacyId = pharmacy.getPharmacyId();
            pharmacyMap.put(pharmacyId, pharmacy);

            double successRate = p.isSuccess() ? 1.0 : 0.0; // ✅ 하루 단위 기준

            pharmacySuccessRates
                    .computeIfAbsent(pharmacyId, k -> new ArrayList<>())
                    .add(successRate);
        }

        // 등급 계산
        List<PharmacyGradeDto> allResults = new ArrayList<>();
        for (Map.Entry<Long, List<Double>> entry : pharmacySuccessRates.entrySet()) {
            Long pharmacyId = entry.getKey();
            List<Double> successRates = entry.getValue();

            double average = successRates.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            String grade = getGrade(average);

            Pharmacy pharmacy = pharmacyMap.get(pharmacyId);
            String address = pharmacy.getAddress();

            allResults.add(PharmacyGradeDto.builder()
                    .pharmacyId(pharmacyId)
                    .pharmacyName(pharmacy.getName())
                    .address(address)
                    .averageSuccessRate(average)
                    .participantCount(successRates.size())
                    .grade(grade)
                    .build());
        }

        // 읍면동 기준으로 그룹핑
        return allResults.stream()
                .collect(Collectors.groupingBy(dto -> extractTown(dto.getAddress())));
    }

    /**
     * 평균 성공률 → 약국 등급
     */
    private String getGrade(double avg) {
        if (avg >= 0.9) return "숲 약국";
        else if (avg >= 0.7) return "나무 약국";
        else if (avg >= 0.5) return "새싹 약국";
        else return "등급 없음";
    }

    /**
     * 주소에서 읍/면/동 정보 추출
     * 예: "서울 강남구 역삼동 123" → "역삼동"
     */
    private String extractTown(String address) {
        if (address == null || address.isBlank()) return "기타";

        String[] parts = address.trim().split(" ");
        if (parts.length >= 3) return parts[2]; // 시, 구, 동

        return "기타";
    }
} 