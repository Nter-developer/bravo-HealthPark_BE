package com.kgu.bravoHealthPark.domain.alarm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum Checking {

    Yes("복용 완료"),
    No("미복용");

    private final String name;
}

