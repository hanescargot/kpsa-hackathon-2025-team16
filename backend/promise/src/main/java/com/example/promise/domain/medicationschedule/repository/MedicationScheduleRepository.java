package com.example.promise.domain.medicationschedule.repository;

import com.example.promise.domain.medicationschedule.entity.MedicationSchedule;
import org.springframework.data.repository.CrudRepository;

public interface MedicationScheduleRepository extends CrudRepository<MedicationSchedule,Long> {
}
