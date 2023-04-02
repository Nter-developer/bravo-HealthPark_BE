package com.kgu.bravoHealthPark.domain.alarm.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AlarmForm {
    private String title; // 알람 제목

    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime time; // 알람 시간

    private LocalDate startDate; // 알람 시작일

    private LocalDate endDate; // 알람 종료일
}
