package com.example.promise.domain.medicine.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId;

    private String name;            // 약 이름
    private String imageUrl;        // 약 이미지
    private String shape;           // 모양
    private String color;           // 색상
    private String imprint;         // 인쇄 내용
    private String form;            // 제형 (예: 정제, 캡슐 등)

    public Medicine(String name) {
        this.name = name;
    }
}
