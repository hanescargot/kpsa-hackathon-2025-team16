package com.example.promise.domain.medicationschedule.entity;

import com.example.promise.domain.medicationschedule.entity.status.SlotTime;
import com.example.promise.domain.user.entity.NormalUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationSlot {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private NormalUser user;

    private LocalDate date;                   // 복약 날짜
    @Enumerated(EnumType.STRING)
    private SlotTime slotTime;               // 아침 / 점심 / 저녁

    private Boolean taken;                   // 복약 여부
    private LocalDateTime takenAt;

    @OneToMany(mappedBy = "slot", cascade = CascadeType.ALL)
    private List<MedicationSlotMedicine> medicines;
}
