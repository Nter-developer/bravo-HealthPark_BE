package com.kgu.bravoHealthPark.domain.medicationInfo.domain;

import com.kgu.bravoHealthPark.domain.state.domain.State;
import com.kgu.bravoHealthPark.domain.type.domain.Type;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
public class MedicationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="mi_id")
    private Long miId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private State state;

    private String name;
    private int tablet; //몇 정
    private int days;
    private LocalDate startDate;
    private LocalDate endDate;
    private String memo;

}