package com.example.promise.domain.medicationschedule.service;

import com.example.promise.domain.challenge.service.ChallengeService;
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
import com.example.promise.global.code.status.ErrorStatus;
import com.example.promise.global.exception.GeneralException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final ChallengeService  challengeService;
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

    public int parseDuration(String usage) {
        Pattern pattern = Pattern.compile("(\\d+)일분");
        Matcher matcher = pattern.matcher(usage);
        if (matcher.find()) return Integer.parseInt(matcher.group(1));
        return 1;
    }

    public int parseDoseCount(String usage) {
        // 예: "하루 3회", "1일 2회", "하루 두 번" 등에서 정수 추출
        if (usage == null) return 1;

        usage = usage.replaceAll("[^0-9]", " "); // 숫자 아닌 건 공백 처리
        String[] tokens = usage.trim().split("\\s+");

        for (String token : tokens) {
            try {
                int num = Integer.parseInt(token);
                if (num >= 1 && num <= 10) return num;
            } catch (NumberFormatException ignored) {}
        }

        return 1; // 기본값: 하루 1회
    }

    public int parseDurationDays(String usage) {
        Pattern pattern = Pattern.compile("(\\d{1,4})일분");
        Matcher matcher = pattern.matcher(usage);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }

        return 1;
    }

    // MedicationSlotService.java
    public MsDto.CalendarSummaryDto getMonthlyCalendarSummary(Long userId, int year, int month) {
        NormalUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<MedicationSlot> slots = medicationSlotRepository.findByUserAndDateBetween(user, start, end);
        if (slots.isEmpty()) {
            return MsDto.CalendarSummaryDto.builder()
                    .dailyRates(List.of())
                    .adherenceRate(0)
                    .streakDays(0)
                    .missedDays(0)
                    .build();
        }

        Map<LocalDate, List<MedicationSlot>> groupedByDate = slots.stream()
                .collect(Collectors.groupingBy(MedicationSlot::getDate));

        List<MsDto.DailyAdherenceDto> dailyRates = new ArrayList<>();
        int totalSlotCount = 0;
        int takenSlotCount = 0;
        int missedDays = 0;
        int currentStreak = 0;
        int maxStreak = 0;

        // ❗ 실제 슬롯이 있는 날짜만 순회
        List<LocalDate> datesWithSlots = groupedByDate.keySet().stream().sorted().toList();
        for (LocalDate date : datesWithSlots) {
            List<MedicationSlot> daySlots = groupedByDate.getOrDefault(date, List.of());
            int dayTotal = daySlots.size();
            int dayTaken = (int) daySlots.stream().filter(MedicationSlot::getTaken).count();

            int adherence = (dayTotal == 0) ? 0 : (int) ((dayTaken * 100.0) / dayTotal);
            if (dayTotal > 0 && dayTaken == 0) missedDays++;

            if (dayTotal > 0 && dayTaken == dayTotal) {
                currentStreak++;
                maxStreak = Math.max(maxStreak, currentStreak);
            } else {
                currentStreak = 0;
            }

            dailyRates.add(MsDto.DailyAdherenceDto.builder()
                    .date(date)
                    .adherenceRate(adherence)
                    .build());

            totalSlotCount += dayTotal;
            takenSlotCount += dayTaken;
        }

        int totalRate = totalSlotCount == 0 ? 0 : (int) ((takenSlotCount * 100.0) / totalSlotCount);

        return MsDto.CalendarSummaryDto.builder()
                .dailyRates(dailyRates)
                .adherenceRate(totalRate)
                .missedDays(missedDays)
                .streakDays(maxStreak)
                .build();
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

        // ✅ 복약 슬롯 완료 수 확인
        NormalUser user = slot.getUser();
        LocalDate date = slot.getDate();

        List<MedicationSlot> slots = medicationSlotRepository.findByUserAndDate(user, date);
        long completed = slots.stream().filter(s -> Boolean.TRUE.equals(s.getTaken())).count();

        // ✅ 챌린지 참여 성공 여부 업데이트
        challengeService.updateChallengeProgress(slot.getUser().getId(), slot.getDate());
    }



}