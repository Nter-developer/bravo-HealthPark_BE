package com.kgu.bravoHealthPark.domain.medicationInfo.dto;

import lombok.Data;

@Data
public class MedicationInfoForm {
    private String itemName;
    private int tablet; //몇 정
    private int times;
    private int days;
    private String memo;
}
