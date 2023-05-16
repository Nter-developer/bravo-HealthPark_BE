package com.kgu.bravoHealthPark.domain.medicineInfo.dto;

import com.kgu.bravoHealthPark.domain.medicineInfo.domain.MedicineInfo;
import lombok.Data;

@Data
public class MedicineInfoDto {

    private String entpName; //기업명
    private String itemName; //제품명
    private String efficacy; //효능
    private String useMethod; //사용법
    private String warning; // 주의사항 경고
    private String precautions; //주의사항
    private String interaction; //상호작용
    private String sideEffects; //부작용
    private String storageMethod; //보관법

    public MedicineInfoDto(MedicineInfo medicineInfo){
        this.entpName=medicineInfo.getEntpName();
        this.itemName=medicineInfo.getItemName();
        this.efficacy=medicineInfo.getEfficacy();
        this.useMethod=medicineInfo.getEntpName();
        this.warning=medicineInfo.getWarning();
        this.precautions=medicineInfo.getPrecautions();
        this.interaction=medicineInfo.getInteraction();
        this.sideEffects=medicineInfo.getSideEffects();
        this.storageMethod=medicineInfo.getStorageMethod();
    }
}
