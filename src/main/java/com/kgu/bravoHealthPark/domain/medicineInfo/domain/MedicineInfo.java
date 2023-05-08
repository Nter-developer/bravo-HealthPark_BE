package com.kgu.bravoHealthPark.domain.medicineInfo.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicineInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="medicine_info_id")
    private Long medicineInfoId;

    private String entpName; //기업명
    private String itemName; //제품명
    private String itemSeq; //품목기준코드
    @Column(columnDefinition = "TEXT")
    private String efficacy; //효능
    @Column(columnDefinition = "TEXT")
    private String useMethod; //사용법
    @Column(columnDefinition = "TEXT")
    private String warning; // 주의사항 경고
    @Column(columnDefinition = "TEXT")
    private String precautions; //주의사항
    @Column(columnDefinition = "TEXT")
    private String interaction; //상호작용
    @Column(columnDefinition = "TEXT")
    private String sideEffects; //부작용

    @Column(columnDefinition = "TEXT")
    private String storageMethod; //보관법

    public MedicineInfo(String entpName, String itemName, String itemSeq, String efficacy, String useMethod, String warning, String precautions, String interaction, String sideEffects,String storageMethod) {
        this.entpName = entpName;
        this.itemName = itemName;
        this.itemSeq = itemSeq;
        if(efficacy==null){
            this.efficacy="없음";
        }else{
            this.efficacy = efficacy.replaceAll("\\<.*?\\>", "");
        }

        if(useMethod==null){
            this.useMethod="없음";
        }else{
            this.useMethod = useMethod.replaceAll("\\<.*?\\>", "");
        }
        if(warning==null){
            this.warning="없음";
        }else{
            this.warning = warning.replaceAll("\\<.*?\\>", "");
        }

        if(precautions==null){
            this.precautions="없음";
        }else{
            this.precautions = precautions.replaceAll("\\<.*?\\>", "");
        }

        if(interaction==null){
            this.interaction="없음";
        }else{
            this.interaction = interaction.replaceAll("\\<.*?\\>", "");
        }

        if(sideEffects==null){
            this.sideEffects="없음";
        }else{
            this.sideEffects = sideEffects.replaceAll("\\<.*?\\>", "");
        }

        if(storageMethod==null){
            this.storageMethod="없음";
        }else{
            this.storageMethod=storageMethod.replaceAll("\\<.*?\\>", "");
        }

//        this.efficacy = efficacy.replaceAll("\\<.*?\\>", "");
//        this.useMethod = useMethod.replaceAll("\\<.*?\\>", "");
//        this.warning = warning.replaceAll("\\<.*?\\>", "");
//        this.precautions = precautions.replaceAll("\\<.*?\\>", "");
//        this.interaction = interaction.replaceAll("\\<.*?\\>", "");
//        this.sideEffects = sideEffects.replaceAll("\\<.*?\\>", "");
//        this.storageMethod=storageMethod.replaceAll("\\<.*?\\>", "");
    }
}
