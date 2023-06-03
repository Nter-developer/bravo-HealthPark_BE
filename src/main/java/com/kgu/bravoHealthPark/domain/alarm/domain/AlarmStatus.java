package com.kgu.bravoHealthPark.domain.alarm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmStatus {

    NOT_CONFIRMED("확인되지 않음"),
    NOT_DOSE("확인하고 복용하지 않음"),
    DOSE("확인하고 복용함");

    @JsonValue
    private final String name;
}

