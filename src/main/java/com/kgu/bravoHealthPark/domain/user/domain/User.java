package com.kgu.bravoHealthPark.domain.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "name", nullable = false)
    private String name;


    private String encPhoneNumber;

    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;

    public String encodePhoneNumber(PasswordEncoder passwordEncoder){
        return passwordEncoder.encode(phoneNumber);
    }

    @Builder
    private User(String phoneNumber, String name,int age,String encPhoneNumber,Role role) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.age=age;
        this.encPhoneNumber=encPhoneNumber;
        this.role=role;
    }

}