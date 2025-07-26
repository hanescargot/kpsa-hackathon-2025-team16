package com.example.promise.domain.medicationschedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class MsDto {

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SlotTakenRequest {
        private boolean taken;
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
