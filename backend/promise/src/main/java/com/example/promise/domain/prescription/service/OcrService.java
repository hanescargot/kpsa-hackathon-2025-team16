package com.example.promise.domain.prescription.service;


import com.example.promise.domain.medicine.entity.Medicine;

import com.example.promise.domain.medicine.repository.MedicineRepository;
import com.example.promise.domain.prescription.dto.AiRequestDto;
import com.example.promise.domain.prescription.dto.ResultDto;
import com.example.promise.domain.prescription.entity.Prescription;
import com.example.promise.domain.prescription.entity.PrescriptionMedicine;
import com.example.promise.domain.prescription.repository.PrescriptionMedicineRepository;
import com.example.promise.domain.prescription.repository.PrescriptionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OcrService {

    private final GoogleOcrService googleOcrService;
    private final ChatGPTService chatGPTService;
    private final MedicineRepository medicineRepository;
    private final  PrescriptionRepository prescriptionRepository;
    private final PrescriptionMedicineRepository prescriptionMedicineRepository;

    public ResultDto.OcrResultDto process(MultipartFile imageFile) throws IOException {
        List<String> ocrTexts = googleOcrService.extractTextFromImage(imageFile);
        String combinedText = String.join(" ", ocrTexts);

        AiRequestDto dto = AiRequestDto.builder()
                .model("gpt-4") // 또는 gpt-3.5-turbo
                .prompt(combinedText)
                .temperature(0.3f)
                .build();

        // GPT 호출
        String aiResponse = chatGPTService.chat(dto).getBody();

        // GPT 응답 파싱
        ObjectMapper mapper = new ObjectMapper();
        JsonNode array = mapper.readTree(aiResponse);

        if (!array.isArray() || array.size() == 0) {
            return new ResultDto.OcrResultDto(false, "처방 정보 없음", null);
        }

// 공통 정보 (첫 번째 요소 기준)
        JsonNode first = array.get(0);
        String pharmacy = first.path("약국명").asText();
        String date = first.path("조제일자").asText();

// 처방 저장 (한 번만!)
        Prescription prescription = prescriptionRepository.save(
                Prescription.builder()
                        .hospitalName(pharmacy)
                        .prescribedAt(LocalDate.parse(date))
                        .viaOcr(true)
                        .isVerified(false)
                        .build()
        );

// 약별 반복 처리
        for (JsonNode obj : array) {
            String name = obj.path("약이름").asText();
            String usage = obj.path("복약안내").asText();
            String effect = obj.path("효능").asText();
            String caution = obj.path("부작용").asText();

            Medicine medicine = medicineRepository.findByName(name)
                    .orElseGet(() -> medicineRepository.save(new Medicine(name)));

            prescriptionMedicineRepository.save(PrescriptionMedicine.builder()
                    .prescription(prescription)
                    .medicine(medicine)
                    .caution(caution)
                    .usageDescription(usage)
                    .effect(effect)
                    .build());
        }

        return new ResultDto.OcrResultDto(true, "성공", null);
    }

}