package com.kgu.bravoHealthPark.domain.alarm.domain;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.user.domain.User;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    @Column(name = "alarm_id")
    private Long alarmId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "med_Info_id")
    private MedicationInfo medicationInfo;

    private String title; // 알람 제목

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time; // 알람 시간

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate; // 알람 시작일

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate; // 알람 종료일

    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus; // 알람 확인, 복용 상태

    public Alarm(MedicationInfo medicationInfo, String title, LocalTime time) {
        this.medicationInfo = medicationInfo;
        this.title = title;
        this.time = time;
        this.startDate = medicationInfo.getStartDate();
        this.endDate = medicationInfo.getEndDate();
    }

    public void updateAlarm(String title, LocalTime time, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.time = time;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void initStatus() {
        this.alarmStatus = AlarmStatus.NOT_CONFIRMED;
    }

    public void changeAlarmStatus(AlarmStatus alarmStatus) {
        this.alarmStatus = alarmStatus;
    }
}