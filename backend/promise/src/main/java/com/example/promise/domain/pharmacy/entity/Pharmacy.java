package com.example.promise.domain.pharmacy.entity;

import com.example.promise.domain.pharmacy.entity.status.PharmacyGrade;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pharmacyId;

    private String name;

    private String phone;

    private String address;

    private Double lat;

    private Double lng;

    private String openHours;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PharmacyGrade grade=PharmacyGrade.SEEDLING;

}
