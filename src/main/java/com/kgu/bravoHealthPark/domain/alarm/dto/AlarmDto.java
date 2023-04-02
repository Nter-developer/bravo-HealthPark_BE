package com.kgu.bravoHealthPark.domain.alarm.dto;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AlarmDto {

    private String title;

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;


    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;

    public AlarmDto(Alarm alarm) {
        this.title = alarm.getTitle();
        this.time = alarm.getTime();
        this.startDate = alarm.getStartDate();
        this.endDate = alarm.getEndDate();
        this.alarmStatus = alarm.getAlarmStatus();
    }
}

