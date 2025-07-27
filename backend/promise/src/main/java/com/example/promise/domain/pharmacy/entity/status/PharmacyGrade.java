package com.example.promise.domain.pharmacy.entity.status;

import lombok.Getter;

@Getter
public enum PharmacyGrade {
    SEEDLING("새싹"),
    TREE("나무"),
    FOREST("숲");

    private final String koreanName;

    PharmacyGrade(String koreanName) {
        this.koreanName = koreanName;
    }
}