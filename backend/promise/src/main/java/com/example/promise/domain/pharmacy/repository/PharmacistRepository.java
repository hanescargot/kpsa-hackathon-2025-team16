package com.example.promise.domain.pharmacy.repository;

import com.example.promise.domain.pharmacist.entity.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacistRepository extends JpaRepository<Pharmacist,Long> {
}
