package com.kgu.bravoHealthPark.domain.medicationInfo.domain;

import com.kgu.bravoHealthPark.domain.state.domain.State;
import com.kgu.bravoHealthPark.domain.type.domain.Type;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="med_Info_id")
    private Long medInfoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private State state;

    private String entpName;
    private String itemName;
    private int tablet; //몇 정
    private int days;
    private LocalDate startDate;
    private LocalDate endDate;
    private String memo;

    //처음 만들어졌을때 상태로 복용중인 상태
    public void firstState(){
        this.state=State.DOING;
    }

    //약 다먹었을때 복용 상태
    public void lastState(){
        this.state=State.FINISHED;
    }

    public void changeState(){
        if(this.state==State.DOING)
            this.state=State.FINISHED;
        else
            this.state=State.DOING;
    }

    public void updateInfo(String entpName,String itemName,int tablet,LocalDate startDate,LocalDate endDate){
        this.entpName=entpName;
        this.itemName=itemName;
        this.tablet=tablet;
        this.startDate=startDate;
        this.endDate=endDate;
    }
    public void updateType(Type type) {
        this.type = type;
    }

    public MedicationInfo(User user, MedicationInfoForm medicationInfoForm,Type type) {
        this.user = user;
        this.type = type;
        this.entpName = medicationInfoForm.getEnptName();
        this.itemName = medicationInfoForm.getItemName();
        this.tablet = medicationInfoForm.getTablet();
        this.days = medicationInfoForm.getDays();
        this.startDate = medicationInfoForm.getStartDate();
        this.endDate = medicationInfoForm.getEndDate();
        this.memo = medicationInfoForm.getMemo();
    }
}