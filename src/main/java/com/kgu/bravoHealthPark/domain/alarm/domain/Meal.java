package com.kgu.bravoHealthPark.domain.alarm.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Meal {

    BEFORE_MEAL("식전 30분"),
    NOW("직전,직후"),
    AFTER_MEAL("식후 30분");

    @JsonValue // enum 타입 둘중에 하나 선택할 수 있게 설정
    private final String name;
}

