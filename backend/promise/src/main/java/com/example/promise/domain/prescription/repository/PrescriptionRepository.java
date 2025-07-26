package com.example.promise.domain.prescription.repository;

import com.example.promise.domain.prescription.entity.Prescription;
import com.example.promise.domain.prescription.entity.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
