package com.kgu.bravoHealthPark.domain.state.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@AllArgsConstructor
public enum State {

    DOING("복용중"),
    FINISHED("복용완료");
    private final String name;
}