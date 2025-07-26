package com.example.promise.domain.pharmacy.service;

import com.example.promise.domain.medicationschedule.entity.MedicationSlot;
import com.example.promise.domain.medicationschedule.repository.MedicationSlotRepository;
import com.example.promise.domain.pharmacy.dto.PharmacyGradeDto;
import com.example.promise.domain.pharmacy.entity.Pharmacy;
import com.example.promise.domain.prescription.entity.Prescription;
import com.example.promise.domain.user.entity.NormalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PharmacyGradeService {

    private final MedicationSlotRepository slotRepository;

    public Map<String, List<PharmacyGradeDto>> calculatePharmacyGradesByRegion() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusMonths(1).withDayOfMonth(1); // 전월 1일
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth()); // 전월 말일

        // 전월 복약 슬롯 전체 조회
        List<MedicationSlot> slots = slotRepository.findByDateBetween(startDate, endDate);

        // 약국별 복약 슬롯 분류
        Map<Pharmacy, List<MedicationSlot>> pharmacySlotMap = new HashMap<>();

        for (MedicationSlot slot : slots) {
            Prescription prescription = getPrescriptionFromSlot(slot);
            if (prescription == null || prescription.getPharmacy() == null) continue;

            Pharmacy pharmacy = prescription.getPharmacy();
            pharmacySlotMap.computeIfAbsent(pharmacy, k -> new ArrayList<>()).add(slot);
        }

        List<PharmacyGradeDto> gradeList = new ArrayList<>();

        for (Map.Entry<Pharmacy, List<MedicationSlot>> entry : pharmacySlotMap.entrySet()) {
            Pharmacy pharmacy = entry.getKey();
            List<MedicationSlot> slotList = entry.getValue();

            // 유저 수 집계
            Set<Long> uniqueUserIds = slotList.stream()
                    .map(s -> s.getUser().getId())
                    .collect(Collectors.toSet());

            int totalUsers = uniqueUserIds.size();

            // 복약 성공 슬롯 수
            long totalSuccess = slotList.stream()
                    .filter(MedicationSlot::getTaken)
                    .count();

            // 성공률 = 총 성공 슬롯 / 유저 수
            double average = totalUsers == 0 ? 0.0 : (double) totalSuccess / totalUsers;

            gradeList.add(PharmacyGradeDto.builder()
                    .pharmacyId(pharmacy.getPharmacyId())
                    .pharmacyName(pharmacy.getName())
                    .address(pharmacy.getAddress())
                    .averageSuccessRate(average)
                    .participantCount(totalUsers)
                    .grade(getGrade(average))
                    .build());
        }

        return gradeList.stream()
                .collect(Collectors.groupingBy(dto -> extractTown(dto.getAddress())));
    }

    private Prescription getPrescriptionFromSlot(MedicationSlot slot) {
        return slot.getMedicines().stream()
                .findFirst()
                .map(msm -> msm.getPrescriptionMedicine().getPrescription())
                .orElse(null);
    }

    private String getGrade(double avg) {
        if (avg >= 90) return "숲 약국";
        else if (avg >= 70) return "나무 약국";
        else if (avg >= 50) return "새싹 약국";
        else return "등급 없음";
    }

    private String extractTown(String address) {
        if (address == null || address.isBlank()) return "기타";
        String[] parts = address.trim().split(" ");
        if (parts.length >= 3) return parts[2];
        return "기타";
    }
}
