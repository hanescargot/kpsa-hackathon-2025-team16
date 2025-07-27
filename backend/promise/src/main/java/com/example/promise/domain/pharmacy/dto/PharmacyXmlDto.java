package com.example.promise.domain.pharmacy.dto;

import lombok.Data;

@Data
public class PharmacyXmlDto {
    private String dutyName;    // 약국 이름
    private String dutyTel1;    // 전화번호
    private String dutyAddr;    // 주소
    private String wgs84Lat;    // 위도
    private String wgs84Lon;    // 경도

    private String dutyTime1s;  // 월요일 시작시간
    private String dutyTime1c;  // 월요일 종료시간
    private String dutyTime2s;  // 화요일 시작시간
    private String dutyTime2c;  // 화요일 종료시간
    private String dutyTime3s;
    private String dutyTime3c;
    private String dutyTime4s;
    private String dutyTime4c;
    private String dutyTime5s;
    private String dutyTime5c;
    private String dutyTime6s;
    private String dutyTime6c;
    private String dutyTime7s;
    private String dutyTime7c;
}
