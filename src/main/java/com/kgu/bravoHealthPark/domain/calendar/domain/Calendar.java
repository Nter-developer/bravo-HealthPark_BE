package com.kgu.bravoHealthPark.domain.calendar.domain;

import com.kgu.bravoHealthPark.domain.confirmation.domain.Confirmation;
import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="calendar_id")
    private Long calendarId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mi_id")
    private MedicationInfo medicationInfo;

    @Enumerated(EnumType.STRING)
    private Confirmation confirmation;

    private int year;
    private int month;
    private int day;


}
