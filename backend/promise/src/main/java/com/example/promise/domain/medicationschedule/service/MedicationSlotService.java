package com.example.promise.domain.medicationschedule.service;

import com.example.promise.domain.medicationschedule.dto.MsDto;
import com.example.promise.domain.medicationschedule.entity.MedicationSlot;
import com.example.promise.domain.medicationschedule.entity.MedicationSlotMedicine;
import com.example.promise.domain.medicationschedule.entity.status.SlotTime;
import com.example.promise.domain.medicationschedule.repository.MedicationSlotMedicineRepository;
import com.example.promise.domain.medicationschedule.repository.MedicationSlotRepository;
import com.example.promise.domain.prescription.entity.PrescriptionMedicine;
import com.example.promise.domain.prescription.repository.PrescriptionMedicineRepository;
import com.example.promise.domain.user.entity.NormalUser;
import com.example.promise.domain.user.repository.NormalUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationSlotService {

    private final PrescriptionMedicineRepository prescriptionMedicineRepository;
    private final MedicationSlotMedicineRepository medicationSlotMedicineRepository;
    private final MedicationSlotRepository medicationSlotRepository;
    private final NormalUserRepository userRepository;
    /**
     * 처방을 받아 현재 날짜 기준으로 복약 스케줄 생성
     */
    @Transactional
    public void generateSlots(NormalUser user, PrescriptionMedicine pm) {
        String usage = pm.getUsageDescription();
        int timesPerDay = parseTimesPerDay(usage);
        int duration = parseDuration(usage);

        List<SlotTime> slotTimes = getSlotTimes(timesPerDay);  // 아침, 점심, 저녁 매핑
        LocalDate startDate = LocalDate.now();

        for (int day = 0; day < duration; day++) {
            LocalDate targetDate = startDate.plusDays(day);

            for (SlotTime slotTime : slotTimes) {
                // 1. 이미 해당 날짜+시간대 Slot이 있으면 가져오고, 없으면 새로 생성
                MedicationSlot slot = medicationSlotRepository
                        .findByUserAndDateAndSlotTime(user, targetDate, slotTime)
                        .orElseGet(() -> {
                            MedicationSlot newSlot = MedicationSlot.builder()
                                    .user(user)
                                    .date(targetDate)
                                    .slotTime(slotTime)
                                    .taken(false)
                                    .build();
                            return medicationSlotRepository.save(newSlot);
                        });

                // 2. Slot에 해당 약을 추가
                MedicationSlotMedicine slotMedicine = MedicationSlotMedicine.builder()
                        .slot(slot)
                        .prescriptionMedicine(pm)
                        .build();

                medicationSlotMedicineRepository.save(slotMedicine);
            }
        }
    }

    private List<SlotTime> getSlotTimes(int timesPerDay) {
        if (timesPerDay == 3) return List.of(SlotTime.MORNING, SlotTime.LUNCH, SlotTime.EVENING);
        if (timesPerDay == 2) return List.of(SlotTime.MORNING, SlotTime.EVENING);
        return List.of(SlotTime.MORNING);
    }

    private int parseTimesPerDay(String usage) {
        if (usage.contains("3회")) return 3;
        if (usage.contains("2회")) return 2;
        if (usage.contains("1회")) return 1;
        return 1;
    }

    private int parseDuration(String usage) {
        Pattern pattern = Pattern.compile("(\\d+)일분");
        Matcher matcher = pattern.matcher(usage);
        if (matcher.find()) return Integer.parseInt(matcher.group(1));
        return 1;
    }

    private List<LocalTime> getDefaultTimes(int timesPerDay) {
        if (timesPerDay == 3)
            return List.of(LocalTime.of(8, 0), LocalTime.of(12, 0), LocalTime.of(18, 0));
        if (timesPerDay == 2)
            return List.of(LocalTime.of(8, 0), LocalTime.of(18, 0));
        return List.of(LocalTime.of(8, 0));
    }


    public List<LocalDate> getSlotDates(Long userId, int year, int month) {
        NormalUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        return medicationSlotRepository.findByUserAndDateBetween(user, start, end).stream()
                .map(MedicationSlot::getDate)
                .distinct()
                .collect(Collectors.toList());
    }

    public MsDto.DailySlotResponse getSlotsByDate(Long userId, LocalDate date) {
        NormalUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<MedicationSlot> slots = medicationSlotRepository.findByUserAndDate(user, date);

        List<MsDto.DailySlotResponse.SlotDto> result = slots.stream().map(slot -> {
            List<String> meds = slot.getMedicines().stream()
                    .map(sm -> sm.getPrescriptionMedicine().getMedicine().getName())
                    .collect(Collectors.toList());
            return new MsDto.DailySlotResponse.SlotDto(slot.getId(), slot.getSlotTime().name(), Boolean.TRUE.equals(slot.getTaken()), meds);
        }).collect(Collectors.toList());

        return new MsDto.DailySlotResponse(date.toString(), result);
    }


    @Transactional
    public void updateTakenStatus(Long slotId, boolean taken) {
        MedicationSlot slot = medicationSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));
        slot.setTaken(taken);
        slot.setTakenAt(taken ? LocalDateTime.now() : null);
    }

}