package com.example.promise.domain.prescription.service;


import com.example.promise.domain.challenge.dto.ChallengeRequestDto;
import com.example.promise.domain.challenge.dto.ChallengeResponseDto;
import com.example.promise.domain.challenge.service.ChallengeService;
import com.example.promise.domain.medicationschedule.service.MedicationSlotService;
import com.example.promise.domain.medicine.entity.Medicine;

import com.example.promise.domain.medicine.repository.MedicineRepository;
import com.example.promise.domain.pharmacy.entity.Pharmacy;
import com.example.promise.domain.pharmacy.repository.PharmacyRepository;
import com.example.promise.domain.prescription.dto.AiRequestDto;
import com.example.promise.domain.prescription.dto.ResultDto;
import com.example.promise.domain.prescription.entity.Prescription;
import com.example.promise.domain.prescription.entity.PrescriptionMedicine;
import com.example.promise.domain.prescription.repository.PrescriptionMedicineRepository;
import com.example.promise.domain.prescription.repository.PrescriptionRepository;
import com.example.promise.domain.user.entity.NormalUser;
import com.example.promise.domain.user.repository.NormalUserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OcrService {

    private final GoogleOcrService googleOcrService;
    private final ChatGPTService chatGPTService;
    private final MedicineRepository medicineRepository;
    private final  PrescriptionRepository prescriptionRepository;
    private final PrescriptionMedicineRepository prescriptionMedicineRepository;
    private final NormalUserRepository normalUserRepository;
    private final MedicationSlotService medicationSlotService;
    private final ChallengeService challengeService;
    private final PharmacyRepository pharmacyRepository;

    public ResultDto.OcrPreviewDto process(MultipartFile imageFile) throws IOException {
        List<String> ocrTexts = googleOcrService.extractTextFromImage(imageFile);
        String combinedText = String.join(" ", ocrTexts);

        AiRequestDto dto = AiRequestDto.builder()
                .model("gpt-4")
                .prompt(combinedText)
                .temperature(0.3f)
                .build();

        String aiResponse = chatGPTService.chat(dto).getBody();
      System.out.println("AI 응답 전체: " + aiResponse);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode array = mapper.readTree(aiResponse);

        if (!array.isArray() || array.size() == 0) {
            throw new IllegalArgumentException("AI 분석 결과가 비어 있음");
        }

        JsonNode first = array.get(0);
        String pharmacy = first.path("약국명").asText();
        String date = first.path("조제일자").asText();
        String doctor = first.path("조제약사").asText();       // 🔹 조제약사
        String patient = first.path("환자정보").asText();       // 🔹 환자정보
        String address = first.path("약국주소").asText();

        List<ResultDto.OcrPreviewDto.OcrMedicineDto> medicineDtos = new ArrayList<>();

        for (JsonNode obj : array) {
            medicineDtos.add(new ResultDto.OcrPreviewDto.OcrMedicineDto(
                    obj.path("약이름").asText(),
                    obj.path("복약안내").asText(),
                    obj.path("효능").asText(),
                    obj.path("부작용").asText()
            ));
        }

        return new ResultDto.OcrPreviewDto(pharmacy, date, doctor, patient, address, medicineDtos);
    }

    public ResultDto.OcrResultDto saveAnalyzedData(ResultDto.OcrPreviewDto previewDto, Long userId) {
        NormalUser user = normalUserRepository.findById(userId).get();

        Pharmacy pharmacy = pharmacyRepository.findByAddress(previewDto.getAddress())
                .orElseGet(() -> {
                    // 없으면 이름만 저장
                    return pharmacyRepository.save(
                            Pharmacy.builder()
                                    .name(previewDto.getPharmacyName())
                                    .address(previewDto.getAddress())
                                    .build()
                    );
                });

        Prescription prescription = prescriptionRepository.save(
                Prescription.builder()
                        .user(user)
                        .hospitalName(previewDto.getPharmacyName())
                        .prescribedAt(LocalDate.parse(previewDto.getPrescribedDate()))
                        .doctorName(previewDto.getDoctorName())    // 🔹 저장
                        .patientName(previewDto.getPatientName())  // 🔹 저장
                        .viaOcr(true)
                        .isVerified(false)
                        .pharmacy(pharmacy)
                        .build()
        );

        for (ResultDto.OcrPreviewDto.OcrMedicineDto m : previewDto.getMedicines()) {
            Medicine medicine = medicineRepository.findByName(m.getName())
                    .orElseGet(() -> medicineRepository.save(new Medicine(m.getName())));

            PrescriptionMedicine pm= prescriptionMedicineRepository.save(PrescriptionMedicine.builder()
                    .prescription(prescription)
                    .medicine(medicine)
                    .usageDescription(m.getUsage())
                    .effect(m.getEffect())
                    .caution(m.getCaution())
                    .build());

            medicationSlotService.generateSlots(user, pm);

            // 🔹 복약 챌린지 자동 참여 처리
            int doseCount = medicationSlotService.parseDoseCount(m.getUsage());  // ex: 하루 3회라면 3
            LocalDate date = prescription.getPrescribedAt(); // 조제일자 기준

            // 참여 시 group이 생성되지 않았으면 자동 생성되고,
            // 자동으로 1인당 100포인트 추가됨
            challengeService.participate(userId, date, doseCount);

        }


        return new ResultDto.OcrResultDto(true, "저장 성공", prescription.getId());
    }



}