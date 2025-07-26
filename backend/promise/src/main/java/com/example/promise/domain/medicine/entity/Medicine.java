package com.example.promise.domain.medicine.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId;

    private String name;

    private String description;

    private String sideEffect;

    private String imageUrl;
}