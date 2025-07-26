// ✅ ConsultationController
package com.example.promise.domain.consultation.controller;

import com.example.promise.domain.consultation.dto.*;
import com.example.promise.domain.consultation.service.ConsultationService;
import com.example.promise.domain.user.entity.BaseUser;
import com.example.promise.global.auth.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
@Tag(name = "Consultation", description = "상담 관련 API")
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping
    @Operation(summary = "상담 생성", description = "사용자가 약사에게 상담을 요청하고 첫 메시지를 보냅니다. 토큰 필요")
    public ConsultationResponseDto createConsultation(@RequestBody ConsultationRequestDto request,
                                                      @Parameter(hidden = true) @AuthUser Long userId) {
        return consultationService.createConsultation(userId, request);
    }

    @PostMapping("/message")
    @Operation(summary = "상담 메시지 전송", description = "사용자 또는 약사가 상담방에 메시지를 전송합니다. 토큰 필요")
    public void sendMessage(@RequestBody ConsultationMessageRequestDto request,
                            @Parameter(hidden = true) @AuthUser Long userId,
                            @RequestParam(defaultValue = "false") boolean fromPharmacist) {
        consultationService.sendMessage(userId, request, fromPharmacist);
    }

    @GetMapping("/{consultationId}/messages")
    @Operation(summary = "상담 메시지 목록 조회", description = "해당 상담방의 메시지를 시간순으로 조회합니다.")
    public List<ConsultationMessageResponseDto> getMessages(@PathVariable Long consultationId) {
        return consultationService.getMessages(consultationId);
    }
}
