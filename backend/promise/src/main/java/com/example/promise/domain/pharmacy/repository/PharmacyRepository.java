package com.example.promise.domain.pharmacy.repository;

import com.example.promise.domain.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PharmacyRepository extends JpaRepository<Pharmacy,Long> {
    Optional<Pharmacy> findByName(String name);
    Optional<Pharmacy> findByAddress(String address);

}
