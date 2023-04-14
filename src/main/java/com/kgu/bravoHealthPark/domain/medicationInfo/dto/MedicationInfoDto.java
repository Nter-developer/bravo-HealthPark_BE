package com.kgu.bravoHealthPark.domain.medicationInfo.dto;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.state.domain.State;
import com.kgu.bravoHealthPark.domain.type.domain.Type;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicationInfoDto {
    private State state;
    private Type type;
    private String entpName;
    private String itemName;
    private int tablet; //몇 정
    private int days;
    private LocalDate startDate;
    private LocalDate endDate;
    private String memo;

    public MedicationInfoDto(MedicationInfo medicationInfo) {
        this.state = medicationInfo.getState();
        this.type = medicationInfo.getType();
        this.entpName = medicationInfo.getEntpName();
        this.itemName = medicationInfo.getItemName();
        this.tablet = medicationInfo.getTablet();
        this.days = medicationInfo.getDays();
        this.startDate = medicationInfo.getStartDate();
        this.endDate = medicationInfo.getEndDate();
        this.memo = medicationInfo.getMemo();
    }
}
