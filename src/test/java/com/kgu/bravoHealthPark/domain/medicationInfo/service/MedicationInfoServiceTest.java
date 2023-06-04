/*
package com.kgu.bravoHealthPark.domain.medicationInfo.service;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoForm;
import com.kgu.bravoHealthPark.domain.medicationInfo.repository.MedicationInfoRepository;
import com.kgu.bravoHealthPark.domain.state.domain.State;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MedicationInfoServiceTest {

    @Autowired
    private MedicationInfoService medicationInfoService;

    @Autowired
    private MedicationInfoRepository medicationInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void updateInfoTest() {
        //given

        User user = User.builder()
                .phoneNumber("010-4851-1564")
                .loginId("김철수")
                .build();

        User saveUser = userRepository.save(user);

        MedicationInfoForm medicationInfoForm1 = new MedicationInfoForm();
        medicationInfoForm1.setDays(4);
        medicationInfoForm1.setMemo("매일 먹어야함");
        medicationInfoForm1.setTablet(4);
        medicationInfoForm1.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm1.setItemName("멀티비타민");
        medicationInfoForm1.setEnptName("경기제약");

        MedicationInfo medicationInfo = new MedicationInfo(saveUser, LocalDate.now(), medicationInfoForm1);
        medicationInfo.firstState();
        MedicationInfo saveMedicationInfo = medicationInfoRepository.save(medicationInfo);

        MedicationInfoForm medicationInfoForm2 = new MedicationInfoForm();
        medicationInfoForm2.setDays(5);
        medicationInfoForm2.setMemo("아침에 먹어야함");
        medicationInfoForm2.setTablet(5);
        medicationInfoForm2.setEndDate(LocalDate.parse("2023-04-20"));
        medicationInfoForm2.setItemName("루테인");
        medicationInfoForm2.setEnptName("아주제약");

        //when

        medicationInfoService.updateInfo(saveMedicationInfo, medicationInfoForm2);

        //then

        assertThat(saveMedicationInfo.getMedInfoId()).isNotNull();
        assertThat(saveMedicationInfo.getDays()).isEqualTo(5);
        assertThat(saveMedicationInfo.getMemo()).isEqualTo("아침에 먹어야함");
        assertThat(saveMedicationInfo.getItemName()).isEqualTo("루테인");
        assertThat(saveMedicationInfo.getEndDate()).isEqualTo(LocalDate.parse("2023-04-20"));
        assertThat(saveMedicationInfo.getTablet()).isEqualTo(5);
    }

    @Test
    public void changeStateTest() {
        //given

        User user = User.builder()
                .phoneNumber("010-4851-1564")
                .loginId("김철수")
                .build();

        User saveUser = userRepository.save(user);

        MedicationInfoForm medicationInfoForm1 = new MedicationInfoForm();
        medicationInfoForm1.setDays(4);
        medicationInfoForm1.setMemo("매일 먹어야함");
        medicationInfoForm1.setTablet(4);
        medicationInfoForm1.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm1.setItemName("멀티비타민");
        medicationInfoForm1.setEnptName("경기제약");

        MedicationInfo medicationInfo = new MedicationInfo(saveUser, LocalDate.now(), medicationInfoForm1);
        medicationInfo.firstState();
        MedicationInfo saveMedicationInfo = medicationInfoRepository.save(medicationInfo);

        //when

        State state1 = saveMedicationInfo.getState();
        medicationInfoService.changeState(saveMedicationInfo);
        State state2 = saveMedicationInfo.getState();

        //then

        assertThat(state1).isEqualTo(State.DOING);
        assertThat(state2).isEqualTo(State.FINISHED);
    }

//    @Test
//    public void updateTypeTest(){
//        //given
//
//        User user= User.builder()
//                .phoneNumber("010-4851-1564")
//                .loginId("김철수")
//                .build();
//
//        User saveUser = userRepository.save(user);
//
//        MedicationInfoForm medicationInfoForm1 = new MedicationInfoForm();
//        medicationInfoForm1.setDays(4);
//        medicationInfoForm1.setMemo("매일 먹어야함");
//        medicationInfoForm1.setTablet(4);
//        medicationInfoForm1.setEndDate(LocalDate.parse("2023-04-18"));
//        medicationInfoForm1.setItemName("멀티비타민");
//        medicationInfoForm1.setEnptName("경기제약");
//
//        MedicationInfo medicationInfo = new MedicationInfo(saveUser, LocalDate.now(), medicationInfoForm1);
//        medicationInfo.firstState();
//        MedicationInfo saveMedicationInfo = medicationInfoRepository.save(medicationInfo);
//
//        //when
//
//        Type type1 = saveMedicationInfo.getType();
//        medicationInfoService.updateType(saveMedicationInfo,Type.Disabetes);
//        Type type2 = saveMedicationInfo.getType();
//        medicationInfoService.updateType(saveMedicationInfo,Type.Lutein);
//        Type type3 = saveMedicationInfo.getType();
//        medicationInfoService.updateType(saveMedicationInfo,Type.VitaminD);
//        Type type4 = saveMedicationInfo.getType();
//
//
//        //then
//
//        assertThat(type1).isEqualTo(Type.VitaminC);
//        assertThat(type2).isEqualTo(Type.Disabetes);
//        assertThat(type3).isEqualTo(Type.Lutein);
//        assertThat(type4).isEqualTo(Type.VitaminD);
//    }
}*/
