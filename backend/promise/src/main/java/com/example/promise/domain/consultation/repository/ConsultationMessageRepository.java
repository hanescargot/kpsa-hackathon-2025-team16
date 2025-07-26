package com.example.promise.domain.consultation.repository;

import com.example.promise.domain.consultation.entity.ConsultationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConsultationMessageRepository extends JpaRepository<ConsultationMessage, Long> {
    List<ConsultationMessage> findByConsultationIdOrderBySentAtAsc(Long consultationId);
    Optional<ConsultationMessage> findTopByConsultationIdOrderBySentAtDesc(Long consultationId);
}