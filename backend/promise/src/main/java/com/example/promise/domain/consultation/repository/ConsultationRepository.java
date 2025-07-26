package com.example.promise.domain.consultation.repository;

import com.example.promise.domain.consultation.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}