package com.kgu.bravoHealthPark.domain.calendar.domain;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long calendarId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;

    private int year;
    private int month;
    private int day;


    public Calendar(Alarm alarm, AlarmStatus alarmStatus,  int year, int month, int day) {

        this.alarm = alarm;
        this.alarmStatus = alarmStatus;
        this.year = year;
        this.month = month;
        this.day = day;
    }

}
