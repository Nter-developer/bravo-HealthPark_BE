package com.kgu.bravoHealthPark.domain.alarm.domain;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="alarm_id")
    private Long alarmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mi_id")
    private MedicationInfo medicationInfo;

    private String title;

    private LocalTime time;
}