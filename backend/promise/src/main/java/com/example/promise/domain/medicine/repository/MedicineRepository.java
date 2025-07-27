package com.example.promise.domain.medicine.repository;

import com.example.promise.domain.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, String> {
    Optional<Medicine> findByName(String name);
}

