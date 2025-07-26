package com.example.promise.domain.prescription.repository;

import com.example.promise.domain.prescription.entity.Prescription;
import com.example.promise.domain.prescription.entity.PrescriptionMedicine;
import com.example.promise.domain.user.entity.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    // PrescriptionRepository
    Optional<Prescription> findTopByUserOrderByPrescribedAtDesc(NormalUser user);
    List<Prescription> findByPharmacy_PharmacyId(Long pharmacyId);

}
