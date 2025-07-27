package com.example.promise.domain.prescription.service;

import com.example.promise.domain.prescription.dto.AiRequestDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ChatGPTService {

    @Value("${openai.secret-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public ResponseEntity<String> chat(AiRequestDto dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        JSONObject requestBody = new JSONObject();

        // chat 모델일 경우 (gpt-3.5-turbo, gpt-4 등)
        if (dto.getModel().startsWith("gpt-")) {
            JSONArray messages = new JSONArray();

            JSONObject system = new JSONObject();
            system.put("role", "system");
            system.put("content", "너는 복약 일정 관리 어플리케이션에서 OCR로 추출된 처방전 텍스트를 분석하고 정보를 정리하는 역할을 맡았어.  \n" +
                    "내가 줄 OCR 텍스트에서 다음 정보를 약별로 정리해서 추출해줘:" +
                    "- 약국명" +
                    "- 조제일자" +
                    "- 조제약사" +
                    "- 환자정보" +
                    "- 약국주소"+
                    "- 약 이름" +
                    "- 복약안내 (ex. 1정씩 2회 7일분)" +
                    "- 효능 또는 효능효과" +
                    "- 부작용 또는 주의사항" +
                    "\n" +
                    "형식은 아래 JSON 리스트 형태로 정리해줘:" +
                    "[" +
                    "  {" +
                    "    \"약국명\": \"호바른마음약국\",\n" +
                    "    \"조제일자\": \"2024-02-11\",\n" +
                    "    \"약이름\": \"히트코나졸정\",\n" +
                    "    \"복약안내\": \"1정씩 2회 7일분\",\n" +
                    "    \"효능\": \"항진균제 - 진균(곰팡이균)에 의한 감염증 예방 및 치료\",\n" +
                    "    \"부작용\": \"처방기간 끝 음주 금지\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"약국명\": \"호바른마음약국\",\n" +
                    "    \"조제일자\": \"2024-02-11\",\n" +
                    "    \"약이름\": \"피디정\",\n" +
                    "    \"복약안내\": \"0.5정씩 2회 7일분\",\n" +
                    "    \"효능\": \"부신피질호르몬제 - 만성 염증, 피부질환, 알러지 치료\",\n" +
                    "    \"부작용\": \"장기 복용 시 상담 필요\"\n" +
                    "  }" +
                    "  ...\n" +
                    "]" +
                    "" +
                    "OCR 텍스트는 아래와 같아:\n");
            messages.add(system);

            JSONObject user = new JSONObject();
            user.put("role", "user");
            user.put("content", dto.getPrompt());
            messages.add(user);

            requestBody.put("model", dto.getModel());
            requestBody.put("messages", messages);
            requestBody.put("temperature", dto.getTemperature());

            String endpoint = "https://api.openai.com/v1/chat/completions";
            return callOpenAiAPI(endpoint, requestBody, headers);
        }

        // 텍스트 생성 모델일 경우 (text-davinci-003 등)
        else {
            requestBody.put("model", dto.getModel());
            requestBody.put("prompt", dto.getPrompt());
            requestBody.put("temperature", dto.getTemperature());
            requestBody.put("max_tokens", 100);

            String endpoint = "https://api.openai.com/v1/completions";
            return callOpenAiAPI(endpoint, requestBody, headers);
        }
    }

    private ResponseEntity<String> callOpenAiAPI(String endpoint, JSONObject requestBody, HttpHeaders headers) {
        try {
            HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
            ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = new ObjectMapper().readTree(response.getBody());

                String result = root.path("choices").get(0).path("message").path("content").asText(null);
                if (result == null) {
                    result = root.path("choices").get(0).path("text").asText(); // completions 전용
                }

                return ResponseEntity.ok(result.trim());
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("OpenAI 호출 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("예외 발생: " + e.getMessage());
        }
    }
}

