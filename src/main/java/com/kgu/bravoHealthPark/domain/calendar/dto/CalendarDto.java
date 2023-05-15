package com.kgu.bravoHealthPark.domain.calendar.dto;

import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import com.kgu.bravoHealthPark.domain.calendar.domain.Calendar;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class CalendarDto {

    @Enumerated(EnumType.STRING)
    private AlarmStatus alarmStatus;

    private int year;
    private int month;
    private int day;

    public CalendarDto(Calendar calendar) {
        this.alarmStatus = calendar.getAlarm().getAlarmStatus();
        this.year = calendar.getYear();
        this.month = calendar.getMonth();
        this.day = calendar.getDay();
    }
}
