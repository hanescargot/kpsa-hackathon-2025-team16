package com.example.promise.domain.medicationschedule.repository;

import com.example.promise.domain.medicationschedule.entity.MedicationSlotMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationSlotMedicineRepository extends JpaRepository<MedicationSlotMedicine, Long> {
}
