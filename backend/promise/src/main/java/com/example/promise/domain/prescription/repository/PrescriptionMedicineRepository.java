package com.example.promise.domain.prescription.repository;

import com.example.promise.domain.prescription.entity.Prescription;
import com.example.promise.domain.prescription.entity.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionMedicineRepository extends JpaRepository<PrescriptionMedicine, Long> {
    List<PrescriptionMedicine> findAllByPrescription(Prescription prescription);
}
