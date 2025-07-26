package com.example.promise.domain.pharmacist.entity;

import com.example.promise.domain.pharmacy.entity.Pharmacy;
import com.example.promise.domain.user.entity.BaseUser;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("PHARMACIST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Pharmacist extends BaseUser {
    private String licenseNumber;

    @OneToOne
    private Pharmacy pharmacy;

    private Long pharmacyVerify;

}
