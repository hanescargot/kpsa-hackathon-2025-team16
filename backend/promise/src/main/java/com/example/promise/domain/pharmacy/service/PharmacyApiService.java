package com.example.promise.domain.pharmacy.service;

import com.example.promise.domain.pharmacy.entity.Pharmacy;
import com.example.promise.domain.pharmacy.repository.PharmacyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacyApiService {

    private final PharmacyRepository pharmacyRepository;

    private final String BASE_URL = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire";

    @Value("${api.pill.service-key}")
    private String serviceKey; // 인코딩되지 않은 원본 키

    @Transactional
    public List<Pharmacy> fetchAndSavePharmacyList(String pharmacyName) {
        try {
            // 1. 쿼리 파라미터 인코딩
            String encodedKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
            String encodedPharmacyName = URLEncoder.encode(pharmacyName, StandardCharsets.UTF_8);

            URI url = UriComponentsBuilder
                    .fromHttpUrl(BASE_URL)
                    .queryParam("serviceKey", encodedKey)
                    .queryParam("QN", encodedPharmacyName)
                    .build(true)
                    .toUri();

            // 2. 요청 헤더 설정 (XML 명시)
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/xml");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // 3. UTF-8로 인코딩된 RestTemplate 생성
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            // 4. 요청 및 응답 수신
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            String xmlResponse = response.getBody();
            System.out.println("응답 XML: \n" + xmlResponse);

            // 5. XML 파싱 → Pharmacy 엔티티 리스트
            List<Pharmacy> pharmacies = parsePharmacyXml(xmlResponse);

            // 6. DB 저장
            pharmacyRepository.saveAll(pharmacies);

            return pharmacies;

        } catch (Exception e) {
            log.error("API 연동 실패: ", e);
            throw new RuntimeException("약국 정보 조회 실패", e);
        }
    }

    private List<Pharmacy> parsePharmacyXml(String xml) {
        List<Pharmacy> result = new ArrayList<>();
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            NodeList items = doc.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Element el = (Element) items.item(i);

                String name = getTagValue("dutyName", el);
                String phone = getTagValue("dutyTel1", el);
                String addr = getTagValue("dutyAddr", el);
                Double lat = Double.parseDouble(getTagValue("wgs84Lat", el));
                Double lng = Double.parseDouble(getTagValue("wgs84Lon", el));
                String openHours = buildOpenHours(el);

                Pharmacy pharmacy = Pharmacy.builder()
                        .name(name)
                        .phone(phone)
                        .address(addr)
                        .lat(lat)
                        .lng(lng)
                        .openHours(openHours)
                        .build();

                result.add(pharmacy);
            }
        } catch (Exception e) {
            log.error("XML 파싱 실패", e);
        }
        return result;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent().trim();
        }
        return null;
    }

    private String buildOpenHours(Element el) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 8; i++) {
            String s = getTagValue("dutyTime" + i + "s", el);
            String c = getTagValue("dutyTime" + i + "c", el);
            if (s != null && c != null) {
                sb.append("요일").append(i).append(": ").append(s).append("~").append(c).append(" ");
            }
        }
        return sb.toString().trim();
    }
}
