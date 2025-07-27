package com.example.promise.domain.guardian.entity;

import com.example.promise.domain.user.entity.NormalUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guardian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private NormalUser user;

    private String name;

    private String relation;

    private String phone;

    private Boolean isConnected;
}