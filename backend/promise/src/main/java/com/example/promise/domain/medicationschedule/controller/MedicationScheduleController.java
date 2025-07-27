package com.example.promise.domain.medicationschedule.controller;

import com.example.promise.domain.medicationschedule.dto.MsDto;
import com.example.promise.domain.medicationschedule.service.MedicationSlotService;
import com.example.promise.global.auth.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "월별 복약 캘린더 요약 조회", description = "사용자의 지정된 월에 해당하는 날짜별 복약률, 전체 복약률, 스트릭 등을 조회합니다.")
    @GetMapping("/calendar")
    public ResponseEntity<?> getMonthlyCalendar(
            @Parameter(hidden = true) @AuthUser Long userId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return ResponseEntity.ok(medicationSlotService.getMonthlyCalendarSummary(userId, year, month));
    }

    @Operation(summary = "일별 복약 스케줄 조회", description = "해당 날짜의 슬롯별 복약 상태와 약 이름 목록을 조회합니다.")
    @GetMapping("/day")
    public ResponseEntity<?> getDailySchedule(
            @Parameter(hidden = true) @AuthUser Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(medicationSlotService.getSlotsByDate(userId, date));
    }

    @Operation(summary = "복약 완료 처리", description = "특정 복약 슬롯에 대해 복약 완료 여부를 업데이트합니다.")
    @PatchMapping("/{slotId}")
    public ResponseEntity<Void> updateSlotTaken(
            @PathVariable Long slotId,
            @RequestBody MsDto.SlotTakenRequest request
    ) {
        medicationSlotService.updateTakenStatus(slotId, request.isTaken());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "복약 시작일과 종료일 조회", description = "사용자의 전체 복약 기간을 조회하여 시작일과 종료일을 반환합니다.")
    @GetMapping("/range")
    public ResponseEntity<MsDto.RangeResponse> getScheduleRange(@AuthUser Long userId) {
        return ResponseEntity.ok(medicationSlotService.getScheduleRange(userId));
    }

}
