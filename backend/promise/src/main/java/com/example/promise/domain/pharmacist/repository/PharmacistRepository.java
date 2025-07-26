package com.example.promise.domain.pharmacist.repository;

import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
}
