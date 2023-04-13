package com.kgu.bravoHealthPark.domain.medicationInfo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicationInfoForm {
    private String enptName;
    private String itemName;
    private int tablet; //몇 정
    private int days;
    private LocalDate startDate;
    private LocalDate endDate;
    private String memo;
}
