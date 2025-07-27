package com.example.promise.domain.medicationschedule.repository;

import com.example.promise.domain.medicationschedule.entity.MedicationSlot;
import com.example.promise.domain.medicationschedule.entity.status.SlotTime;
import com.example.promise.domain.user.entity.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MedicationSlotRepository extends JpaRepository<MedicationSlot,Long> {
    Optional<MedicationSlot> findByUserAndDateAndSlotTime(NormalUser user, LocalDate date, SlotTime slotTime);
    List<MedicationSlot> findByUserAndDateBetween(NormalUser user, LocalDate start, LocalDate end);
    List<MedicationSlot> findByUserAndDate(NormalUser user, LocalDate date);
    List<MedicationSlot> findByUser(NormalUser user);
    // 전월 등급 산정을 위한 슬롯 목록 조회
    List<MedicationSlot> findByDateBetween(LocalDate startDate, LocalDate endDate);

}
