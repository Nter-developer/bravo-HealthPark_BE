package com.kgu.bravoHealthPark.domain.user.domain;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="user_id")
    private Long userId;

    private String phoneNumber;

    private String name;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    private User(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }
}