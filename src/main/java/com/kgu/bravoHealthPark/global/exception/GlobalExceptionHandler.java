package com.kgu.bravoHealthPark.global.exception;

import com.kgu.bravoHealthPark.global.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BravoHealthParkException.class)
    public ResponseEntity<ErrorResponse> bravoHealthParkException(BravoHealthParkException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        LOGGER.error("HandleException call in advice, {}", exception.getErrorCode());

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.from(errorCode));
    }
}
