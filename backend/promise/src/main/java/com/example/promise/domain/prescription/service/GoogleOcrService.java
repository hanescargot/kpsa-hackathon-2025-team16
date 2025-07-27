package com.example.promise.domain.prescription.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GoogleOcrService {
    @Value("${google.application.credentials}") // application.yml에서 값 가져오기
    private String credentialsPath;

    public List<String> extractTextFromImage(MultipartFile file) throws IOException {
        // 🔹 Google Cloud Vision 인증을 위해 JSON 파일을 직접 로드
        InputStream credentialsStream = new ClassPathResource(credentialsPath).getInputStream();
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create(ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(() -> credentials).build())) {
            ByteString imgBytes = ByteString.copyFrom(file.getBytes());
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(List.of(request));
            List<AnnotateImageResponse> responses = response.getResponsesList();
            return responses.stream()
                    .flatMap(res -> res.getTextAnnotationsList().stream())
                    .map(EntityAnnotation::getDescription)
                    .collect(Collectors.toList());
        }
    }
}