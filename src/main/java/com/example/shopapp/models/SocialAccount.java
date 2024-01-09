package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "social_accounts")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider",length = 20,nullable = false)
    private  String provider;
    @Column(name = "provider_id",length = 50,nullable = false)
    private  String providerId;

    @Column(name = "name",length = 150)
    private String name;

    @Column(name = "email",length = 150)
    private String email;


}
