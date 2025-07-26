package com.example.promise.domain.pharmacy.entity;

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
}
