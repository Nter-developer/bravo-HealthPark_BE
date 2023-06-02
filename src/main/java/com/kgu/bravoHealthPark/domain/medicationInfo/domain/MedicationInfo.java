package com.kgu.bravoHealthPark.domain.medicationInfo.domain;

import com.kgu.bravoHealthPark.domain.state.domain.State;
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

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private State state;

    private String itemName;
    private int tablet; //1회 투약량

    private int times; //1회 투여횟수
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

    public void updateInfo(String itemName,int tablet,int times,int days,String memo){
        this.startDate=LocalDate.now();
        this.days=days;
        this.times=times;
        this.itemName=itemName;
        this.endDate = startDate.plusDays(days);
        this.tablet=tablet;
        this.memo =memo;
    }

    public MedicationInfo(User user, LocalDate startDate,MedicationInfoForm medicationInfoForm) {
        this.user = user;
        this.itemName = medicationInfoForm.getItemName();
        this.times=medicationInfoForm.getTimes();
        this.tablet = medicationInfoForm.getTablet();
        this.days = medicationInfoForm.getDays();
        this.startDate = startDate;
        this.endDate = startDate.plusDays(days);
        this.memo = medicationInfoForm.getMemo();
    }

    public MedicationInfo(User user, LocalDate startDate, String itemName,int times,int tablet,int days,String memo){
        this.user=user;
        this.startDate=startDate;
        this.endDate=startDate.plusDays(days);
        this.itemName=itemName;
        this.times=times;
        this.tablet=tablet;
        this.days=days;
        this.memo=memo;
    }
}