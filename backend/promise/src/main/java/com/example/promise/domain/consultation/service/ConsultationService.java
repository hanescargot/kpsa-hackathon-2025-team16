package com.example.promise.domain.consultation.service;

import com.example.promise.domain.consultation.dto.*;
import com.example.promise.domain.consultation.entity.*;
import com.example.promise.domain.consultation.repository.*;
import com.example.promise.domain.pharmacist.entity.Pharmacist;
import com.example.promise.domain.pharmacist.repository.PharmacistRepository;
import com.example.promise.domain.user.entity.NormalUser;
import com.example.promise.domain.user.repository.NormalUserRepository;
import com.example.promise.domain.user.entity.status.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final ConsultationMessageRepository messageRepository;
    private final PharmacistRepository pharmacistRepository;
    private final NormalUserRepository userRepository;

    @Transactional
    public ConsultationResponseDto createConsultation(Long userId, ConsultationRequestDto request) {
        NormalUser user = userRepository.findById(userId).orElseThrow();
        Pharmacist pharmacist = pharmacistRepository.findById(request.getPharmacistId()).orElseThrow();

        Consultation consultation = consultationRepository.save(
                Consultation.builder()
                        .user(user)
                        .pharmacist(pharmacist)
                        .isCompleted(false)
                        .startedAt(LocalDateTime.now())
                        .build()
        );

        messageRepository.save(
                ConsultationMessage.builder()
                        .consultation(consultation)
                        .sender(UserRole.USER)
                        .message(request.getMessage())
                        .sentAt(LocalDateTime.now())
                        .build()
        );

        return new ConsultationResponseDto(consultation.getId());
    }

    @Transactional
    public void sendMessage(Long userId, ConsultationMessageRequestDto request, boolean fromPharmacist) {
        Consultation consultation = consultationRepository.findById(request.getConsultationId()).orElseThrow();

        messageRepository.save(
                ConsultationMessage.builder()
                        .consultation(consultation)
                        .sender(fromPharmacist ? UserRole.PHARMACIST : UserRole.USER)
                        .message(request.getMessage())
                        .sentAt(LocalDateTime.now())
                        .build()
        );
    }

    public List<ConsultationMessageResponseDto> getMessages(Long consultationId) {
        return messageRepository.findByConsultationIdOrderBySentAtAsc(consultationId)
                .stream()
                .map(m -> new ConsultationMessageResponseDto(m.getSender(), m.getMessage(), m.getSentAt()))
                .collect(Collectors.toList());
    }

    public List<ConsultationSummaryDto> getWaitingConsultations(Long pharmacistId) {
        List<Consultation> consultations = consultationRepository.findByPharmacistIdAndIsCompletedFalse(pharmacistId);

        return consultations.stream()
                .map(c -> {
                    // 최신 메시지 하나 조회 (가장 최근)
                    ConsultationMessage latest = messageRepository
                            .findTopByConsultationIdOrderBySentAtDesc(c.getId())
                            .orElse(null);

                    if (latest == null) return null;

                    return new ConsultationSummaryDto(
                            c.getId(),
                            c.getUser().getName(),
                            latest.getMessage(),
                            latest.getSentAt()
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}