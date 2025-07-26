package com.example.promise.domain.medicationschedule.service;

import com.example.promise.domain.medicationschedule.entity.MedicationSchedule;
import com.example.promise.domain.medicationschedule.repository.MedicationScheduleRepository;
import com.example.promise.domain.prescription.entity.Prescription;
import com.example.promise.domain.prescription.entity.PrescriptionMedicine;
import com.example.promise.domain.prescription.repository.PrescriptionMedicineRepository;
import com.example.promise.domain.user.entity.NormalUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MedicationScheduleService {

    private final PrescriptionMedicineRepository prescriptionMedicineRepository;
    private final MedicationScheduleRepository medicationScheduleRepository;

    /**
     * 처방을 받아 현재 날짜 기준으로 복약 스케줄 생성
     */
    @Transactional
    public void generateSchedules(NormalUser user, PrescriptionMedicine pm) {
        String usage = pm.getUsageDescription();
        int timesPerDay = parseTimesPerDay(usage);
        int duration = parseDuration(usage);

        List<LocalTime> times = getDefaultTimes(timesPerDay);
        LocalDate startDate = LocalDate.now();

        for (int day = 0; day < duration; day++) {
            for (LocalTime time : times) {
                LocalDateTime scheduleTime = startDate.plusDays(day).atTime(time);

                MedicationSchedule schedule = MedicationSchedule.builder()
                        .user(user) // 중요
                        .prescriptionMedicine(pm)
                        .scheduledTime(scheduleTime)
                        .taken(false)
                        .missed(false)
                        .build();

                medicationScheduleRepository.save(schedule);
            }
        }
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
}