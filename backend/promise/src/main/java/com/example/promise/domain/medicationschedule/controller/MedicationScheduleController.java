package com.example.promise.domain.medicationschedule.controller;

import com.example.promise.domain.medicationschedule.dto.MsDto;
import com.example.promise.domain.medicationschedule.service.MedicationSlotService;
import com.example.promise.global.auth.AuthUser;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class MedicationScheduleController {

    private final MedicationSlotService medicationSlotService;

    // 1. 월별 캘린더 요약
    @GetMapping("/calendar")
    public ResponseEntity<?> getMonthlyCalendar(
            @Parameter(hidden = true) @AuthUser Long userId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return ResponseEntity.ok(medicationSlotService.getSlotDates(userId, year, month));
    }

    // 2. 일별 상세 스케줄 조회
    @GetMapping("/day")
    public ResponseEntity<?> getDailySchedule(
            @Parameter(hidden = true) @AuthUser Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(medicationSlotService.getSlotsByDate(userId, date));
    }

    // 3. 복약 완료 처리
    @PatchMapping("/{slotId}")
    public ResponseEntity<Void> updateSlotTaken(
            @PathVariable Long slotId,
            @RequestBody MsDto.SlotTakenRequest request
    ) {
        medicationSlotService.updateTakenStatus(slotId, request.isTaken());
        return ResponseEntity.ok().build();
    }

}
