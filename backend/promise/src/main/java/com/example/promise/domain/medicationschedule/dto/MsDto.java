package com.example.promise.domain.medicationschedule.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class MsDto {

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SlotTakenRequest {
        private boolean taken;
    }

    @Getter
    @Builder
    public static class CalendarSummaryDto {
        private List<DailyAdherenceDto> dailyRates; // 날짜별 복약률
        private int adherenceRate; // 전체 복약률 %
        private int streakDays;
        private int missedDays;
    }

    @Getter
    @Builder
    public static class DailyAdherenceDto {
        private LocalDate date;
        private int adherenceRate; // 0~100
    }

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DailySlotResponse {
        private String date;
        private List<SlotDto> slots;

        @Getter @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class SlotDto {
            private Long slotId;
            private String slotTime; // "MORNING" 등
            private boolean taken;
            private List<String> medicineNames;
        }
    }


}
