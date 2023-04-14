package com.kgu.bravoHealthPark.global.exception;

import lombok.Getter;

@Getter
public class BravoHealthParkException extends RuntimeException{
    private final ErrorCode errorCode;

    public BravoHealthParkException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public static BravoHealthParkException type(ErrorCode errorCode) {
        return new BravoHealthParkException(errorCode);
    }
}
