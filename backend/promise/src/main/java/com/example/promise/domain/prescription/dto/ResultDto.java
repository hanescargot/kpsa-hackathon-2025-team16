package com.example.promise.domain.prescription.dto;

import com.example.promise.domain.medicine.entity.Medicine;
import com.example.promise.domain.prescription.entity.Prescription;
import com.example.promise.domain.prescription.entity.PrescriptionMedicine;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


public class ResultDto {

    @Getter
    @AllArgsConstructor
    public static class OcrResultDto {
        private boolean isSuccess;
        private String message;
        private Long prescriptionId;
    }

    @Getter
    @AllArgsConstructor
    public static class PrescribedMedicineDto {
        private String name;
        private String effect;
        private String usage;
        private String caution;

        public PrescriptionMedicine toEntity(Prescription prescription, Medicine medicine) {
            return PrescriptionMedicine.builder()
                    .prescription(prescription)
                    .medicine(medicine)
                    .effect(effect)
                    .usageDescription(usage)
                    .caution(caution)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class PrescriptionDto {
        private LocalDate prescribedAt;
        private String hospitalName;
        private List<PrescribedMedicineDto> medicines;

        public Prescription toEntity() {
            return Prescription.builder()
                    .prescribedAt(prescribedAt)
                    .hospitalName(hospitalName)
                    .viaOcr(true)
                    .isVerified(false)
                    .build();
        }
    }

}
