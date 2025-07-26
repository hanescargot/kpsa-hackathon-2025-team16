package com.example.promise.domain.pharmacist.entity;

import com.example.promise.domain.pharmacy.entity.Pharmacy;
import com.example.promise.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pharmacist {

    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;
}
