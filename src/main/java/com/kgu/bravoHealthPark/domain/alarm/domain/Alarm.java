package com.kgu.bravoHealthPark.domain.alarm.domain;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long alarmId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "med_Info_id")
    private MedicationInfo medicationInfo;

    private String title; // 알람 제목

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time; // 알람 시간

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date; // 알람 시작일

    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus; // 알람 확인, 복용 상태

    @Enumerated(EnumType.STRING)
    private Meal meal; // 알람 확인, 복용 상태

    public Alarm(MedicationInfo medicationInfo, String title, LocalTime time, Meal meal,LocalDate date) {
        this.medicationInfo = medicationInfo;
        this.title = title;
        this.time = time;
        this.meal = meal;
        this.date= date;
    }

    public void deleteMedicationInfo() {
        this.medicationInfo = null;
    }

    public void updateAlarm(String title, LocalTime time, LocalDate date) {
        this.title = title;
        this.time = time;
        this.date = date;
    }

    public void initStatus() {
        this.alarmStatus = AlarmStatus.NOT_CONFIRMED;
    }

    public void changeAlarmStatus(AlarmStatus alarmStatus) {
        this.alarmStatus = alarmStatus;
    }
}