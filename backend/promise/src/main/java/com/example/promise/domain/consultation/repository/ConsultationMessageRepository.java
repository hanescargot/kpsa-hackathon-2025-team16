package com.example.promise.domain.consultation.repository;

import com.example.promise.domain.consultation.entity.ConsultationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationMessageRepository extends JpaRepository<ConsultationMessage, Long> {
    List<ConsultationMessage> findByConsultationIdOrderBySentAtAsc(Long consultationId);
}