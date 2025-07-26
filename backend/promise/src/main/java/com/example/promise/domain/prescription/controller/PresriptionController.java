package com.example.promise.domain.prescription.controller;

import com.example.promise.domain.prescription.dto.ResultDto;
import com.example.promise.domain.prescription.service.GoogleOcrService;
import com.example.promise.global.ApiResponse;
import com.example.promise.global.auth.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.promise.domain.prescription.service.OcrService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/prescription")
@RequiredArgsConstructor
@Tag(name = "Prescription API", description = "처방전 관련 API")
public class PresriptionController {

    private final GoogleOcrService googleOcrService;
    private final OcrService ocrService;


    @PostMapping(value = "/ocrtest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "OCR 테스트", description = "이미지에서 텍스트를 추출하는 API입니다.")
    public ApiResponse<List<String>> createAccountByReceipt(@RequestParam("file") MultipartFile file) {
        try {
            List<String> extractedText = googleOcrService.extractTextFromImage(file);
            System.out.println("추출된 데이터: " + extractedText);
            return ApiResponse.onSuccess(extractedText);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.onFailure("OCR_ERROR", "OCR 처리 중 오류 발생", List.of());
        }
    }

    @PostMapping(value = "/ocr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "OCR", description = "이미지를 OCR로 분석하여 처방 정보를 반환합니다. 토큰 필요")
    public ResponseEntity<ResultDto.OcrPreviewDto> upload(@RequestParam("file") MultipartFile file, @Parameter(hidden = true) @AuthUser Long userId) throws IOException {
        return ResponseEntity.ok(ocrService.process(file));
    }

    @PostMapping("/save")
    public ResponseEntity<ResultDto.OcrResultDto> save(@RequestBody ResultDto.OcrPreviewDto previewDto, @Parameter(hidden = true) @AuthUser Long userId) {
        return ResponseEntity.ok(ocrService.saveAnalyzedData(previewDto, userId));
    }


}
