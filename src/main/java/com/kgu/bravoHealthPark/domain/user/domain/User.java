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
}