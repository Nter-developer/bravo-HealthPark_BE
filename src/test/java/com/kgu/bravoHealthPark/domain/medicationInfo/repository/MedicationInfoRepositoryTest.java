package com.kgu.bravoHealthPark.domain.medicationInfo.repository;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoForm;
import com.kgu.bravoHealthPark.domain.state.domain.State;
import com.kgu.bravoHealthPark.domain.type.domain.Type;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MedicationInfoRepositoryTest {

    @Autowired
    private MedicationInfoRepository medicationInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTest(){
        //given

        User user= User.builder()
                .phoneNumber("010-4851-1564")
                .username("김철수")
                .build();
        MedicationInfoForm medicationInfoForm = new MedicationInfoForm();
        medicationInfoForm.setDays(4);
        medicationInfoForm.setMemo("매일 먹어야함");
        medicationInfoForm.setTablet(4);
        medicationInfoForm.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm.setItemName("멀티비타민");
        medicationInfoForm.setEnptName("경기제약");

        //when

        MedicationInfo medicationInfo = new MedicationInfo(user, LocalDate.now(), medicationInfoForm, Type.VitaminC);
        medicationInfo.firstState();
        MedicationInfo saveMedicationInfo = medicationInfoRepository.save(medicationInfo);

        //then

        assertThat(saveMedicationInfo.getMedInfoId()).isNotNull();
        assertThat(saveMedicationInfo.getDays()).isEqualTo(medicationInfo.getDays());
        assertThat(saveMedicationInfo.getMemo()).isEqualTo(medicationInfo.getMemo());
        assertThat(saveMedicationInfo.getItemName()).isEqualTo(medicationInfo.getItemName());
        assertThat(saveMedicationInfo.getEntpName()).isEqualTo(medicationInfo.getEntpName());
        assertThat(saveMedicationInfo.getStartDate()).isEqualTo(medicationInfo.getStartDate());
        assertThat(saveMedicationInfo.getEndDate()).isEqualTo(medicationInfo.getEndDate());
        assertThat(saveMedicationInfo.getTablet()).isEqualTo(medicationInfo.getTablet());
    }

    @Test
    public void deleteTest(){
        //given

        User user= User.builder()
                .phoneNumber("010-4851-1564")
                .username("김철수")
                .build();
        MedicationInfoForm medicationInfoForm = new MedicationInfoForm();
        medicationInfoForm.setDays(4);
        medicationInfoForm.setMemo("매일 먹어야함");
        medicationInfoForm.setTablet(4);
        medicationInfoForm.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm.setItemName("멀티비타민");
        medicationInfoForm.setEnptName("경기제약");

        //when

        MedicationInfo medicationInfo = new MedicationInfo(user, LocalDate.now(), medicationInfoForm, Type.VitaminC);
        medicationInfo.firstState();
        MedicationInfo saveMedicationInfo = medicationInfoRepository.save(medicationInfo);
        medicationInfoRepository.delete(saveMedicationInfo);
        //then

        assertThat(medicationInfoRepository.findMedicationInfoByMedInfoId(saveMedicationInfo.getMedInfoId())).isNull();
    }

    @Test
    public void findByIdTest(){
        //given

        User user= User.builder()
                .phoneNumber("010-4851-1564")
                .username("김철수")
                .build();

        MedicationInfoForm medicationInfoForm1 = new MedicationInfoForm();
        medicationInfoForm1.setDays(4);
        medicationInfoForm1.setMemo("매일 먹어야함");
        medicationInfoForm1.setTablet(4);
        medicationInfoForm1.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm1.setItemName("멀티비타민");
        medicationInfoForm1.setEnptName("경기제약");

        MedicationInfoForm medicationInfoForm2 = new MedicationInfoForm();
        medicationInfoForm2.setDays(5);
        medicationInfoForm2.setMemo("먹어야함");
        medicationInfoForm2.setTablet(5);
        medicationInfoForm2.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm2.setItemName("당뇨약");
        medicationInfoForm2.setEnptName("경기제약");

        MedicationInfo medicationInfo1 = new MedicationInfo(user, LocalDate.now(), medicationInfoForm1, Type.VitaminC);
        MedicationInfo medicationInfo2 = new MedicationInfo(user, LocalDate.now(), medicationInfoForm2, Type.Disabetes);
        medicationInfo1.firstState();
        medicationInfo2.firstState();
        MedicationInfo saveMedicationInfo1 = medicationInfoRepository.save(medicationInfo1);
        MedicationInfo saveMedicationInfo2 = medicationInfoRepository.save(medicationInfo2);
        medicationInfoRepository.flush();
        //when

        MedicationInfo findInfo1 = medicationInfoRepository.findMedicationInfoByMedInfoId(saveMedicationInfo1.getMedInfoId());
        MedicationInfo findInfo2 = medicationInfoRepository.findMedicationInfoByMedInfoId(saveMedicationInfo2.getMedInfoId());

        //then

        assertThat(findInfo1.getMedInfoId()).isEqualTo(saveMedicationInfo1.getMedInfoId());
        assertThat(findInfo2.getMedInfoId()).isEqualTo(saveMedicationInfo2.getMedInfoId());
    }

    @Test
    public void findAllByUserIdTest(){
        //given

        User user1= User.builder()
                .phoneNumber("010-4851-1564")
                .username("김철수")
                .build();

        User user2= User.builder()
                .phoneNumber("010-1234-5678")
                .username("나나나")
                .build();

        User saveUser1 = userRepository.save(user1);
        User saveUser2 = userRepository.save(user2);


        MedicationInfoForm medicationInfoForm1 = new MedicationInfoForm();
        medicationInfoForm1.setDays(4);
        medicationInfoForm1.setMemo("매일 먹어야함");
        medicationInfoForm1.setTablet(4);
        medicationInfoForm1.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm1.setItemName("멀티비타민");
        medicationInfoForm1.setEnptName("경기제약");

        MedicationInfoForm medicationInfoForm2 = new MedicationInfoForm();
        medicationInfoForm2.setDays(5);
        medicationInfoForm2.setMemo("먹어야함");
        medicationInfoForm2.setTablet(5);
        medicationInfoForm2.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm2.setItemName("당뇨약");
        medicationInfoForm2.setEnptName("경기제약");

        MedicationInfoForm medicationInfoForm3 = new MedicationInfoForm();
        medicationInfoForm3.setDays(5);
        medicationInfoForm3.setMemo("먹어야함");
        medicationInfoForm3.setTablet(5);
        medicationInfoForm3.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm3.setItemName("당뇨약");
        medicationInfoForm3.setEnptName("경기제약");

        MedicationInfoForm medicationInfoForm4 = new MedicationInfoForm();
        medicationInfoForm4.setDays(5);
        medicationInfoForm4.setMemo("먹어야함");
        medicationInfoForm4.setTablet(5);
        medicationInfoForm4.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm4.setItemName("당뇨약");
        medicationInfoForm4.setEnptName("경기제약");

        MedicationInfo medicationInfo1 = new MedicationInfo(saveUser1, LocalDate.now(), medicationInfoForm1, Type.VitaminC);
        MedicationInfo medicationInfo2 = new MedicationInfo(saveUser1, LocalDate.now(), medicationInfoForm2, Type.Disabetes);
        MedicationInfo medicationInfo3 = new MedicationInfo(saveUser2, LocalDate.now(), medicationInfoForm2, Type.Disabetes);
        MedicationInfo medicationInfo4 = new MedicationInfo(saveUser2, LocalDate.now(), medicationInfoForm2, Type.Disabetes);

        medicationInfo1.firstState();
        medicationInfo2.firstState();
        medicationInfo3.firstState();
        medicationInfo4.firstState();

        MedicationInfo saveMedicationInfo1 = medicationInfoRepository.save(medicationInfo1);
        MedicationInfo saveMedicationInfo2 = medicationInfoRepository.save(medicationInfo2);
        MedicationInfo saveMedicationInfo3 = medicationInfoRepository.save(medicationInfo3);
        MedicationInfo saveMedicationInfo4 = medicationInfoRepository.save(medicationInfo4);


        medicationInfoRepository.flush();

        //when

        List<MedicationInfo> findInfo1 = medicationInfoRepository.findAllByUser_UserId(saveUser1.getUserId());
        List<MedicationInfo> findInfo2 = medicationInfoRepository.findAllByUser_UserId(saveUser2.getUserId());

        //then

        assertThat(findInfo1.size()).isEqualTo(2);
        assertThat(findInfo2.size()).isEqualTo(2);

    }

    @Test
    public void findAllByUserIdAndTypeTest(){
        //given

        User user1= User.builder()
                .phoneNumber("010-4851-1564")
                .username("김철수")
                .build();

        User user2= User.builder()
                .phoneNumber("010-1234-5678")
                .username("나나나")
                .build();

        User saveUser1 = userRepository.save(user1);
        User saveUser2 = userRepository.save(user2);


        MedicationInfoForm medicationInfoForm1 = new MedicationInfoForm();
        medicationInfoForm1.setDays(4);
        medicationInfoForm1.setMemo("매일 먹어야함");
        medicationInfoForm1.setTablet(4);
        medicationInfoForm1.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm1.setItemName("멀티비타민");
        medicationInfoForm1.setEnptName("경기제약");

        MedicationInfoForm medicationInfoForm2 = new MedicationInfoForm();
        medicationInfoForm2.setDays(5);
        medicationInfoForm2.setMemo("먹어야함");
        medicationInfoForm2.setTablet(5);
        medicationInfoForm2.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm2.setItemName("당뇨약");
        medicationInfoForm2.setEnptName("경기제약");

        MedicationInfoForm medicationInfoForm3 = new MedicationInfoForm();
        medicationInfoForm3.setDays(5);
        medicationInfoForm3.setMemo("먹어야함");
        medicationInfoForm3.setTablet(5);
        medicationInfoForm3.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm3.setItemName("당뇨약");
        medicationInfoForm3.setEnptName("경기제약");

        MedicationInfoForm medicationInfoForm4 = new MedicationInfoForm();
        medicationInfoForm4.setDays(5);
        medicationInfoForm4.setMemo("먹어야함");
        medicationInfoForm4.setTablet(5);
        medicationInfoForm4.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm4.setItemName("당뇨약");
        medicationInfoForm4.setEnptName("경기제약");

        MedicationInfo medicationInfo1 = new MedicationInfo(saveUser1, LocalDate.now(), medicationInfoForm1, Type.VitaminC);
        MedicationInfo medicationInfo2 = new MedicationInfo(saveUser1, LocalDate.now(), medicationInfoForm2, Type.Disabetes);
        MedicationInfo medicationInfo3 = new MedicationInfo(saveUser2, LocalDate.now(), medicationInfoForm2, Type.Disabetes);
        MedicationInfo medicationInfo4 = new MedicationInfo(saveUser2, LocalDate.now(), medicationInfoForm2, Type.Disabetes);

        medicationInfo1.firstState();
        medicationInfo2.firstState();
        medicationInfo3.lastState();
        medicationInfo4.lastState();

        MedicationInfo saveMedicationInfo1 = medicationInfoRepository.save(medicationInfo1);
        MedicationInfo saveMedicationInfo2 = medicationInfoRepository.save(medicationInfo2);
        MedicationInfo saveMedicationInfo3 = medicationInfoRepository.save(medicationInfo3);
        MedicationInfo saveMedicationInfo4 = medicationInfoRepository.save(medicationInfo4);


        medicationInfoRepository.flush();

        //when

        List<MedicationInfo> findInfo1 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndStateIs(saveUser1.getUserId(), State.DOING);
        List<MedicationInfo> findInfo2 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndStateIs(saveUser1.getUserId(), State.FINISHED);
        List<MedicationInfo> findInfo3 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndStateIs(saveUser2.getUserId(), State.DOING);
        List<MedicationInfo> findInfo4 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndStateIs(saveUser2.getUserId(), State.FINISHED);

        //then

        assertThat(findInfo1.size()).isEqualTo(2);
        assertThat(findInfo2.size()).isEqualTo(0);
        assertThat(findInfo3.size()).isEqualTo(0);
        assertThat(findInfo4.size()).isEqualTo(2);
    }

    @Test
    public void findAllByUserIdAndStateTest(){
        //given

        User user1= User.builder()
                .phoneNumber("010-4851-1564")
                .username("김철수")
                .build();

        User user2= User.builder()
                .phoneNumber("010-1234-5678")
                .username("나나나")
                .build();

        User saveUser1 = userRepository.save(user1);
        User saveUser2 = userRepository.save(user2);


        MedicationInfoForm medicationInfoForm1 = new MedicationInfoForm();
        medicationInfoForm1.setDays(4);
        medicationInfoForm1.setMemo("매일 먹어야함");
        medicationInfoForm1.setTablet(4);
        medicationInfoForm1.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm1.setItemName("멀티비타민");
        medicationInfoForm1.setEnptName("경기제약");

        MedicationInfoForm medicationInfoForm2 = new MedicationInfoForm();
        medicationInfoForm2.setDays(5);
        medicationInfoForm2.setMemo("먹어야함");
        medicationInfoForm2.setTablet(5);
        medicationInfoForm2.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm2.setItemName("당뇨약");
        medicationInfoForm2.setEnptName("경기제약");

        MedicationInfoForm medicationInfoForm3 = new MedicationInfoForm();
        medicationInfoForm3.setDays(5);
        medicationInfoForm3.setMemo("먹어야함");
        medicationInfoForm3.setTablet(5);
        medicationInfoForm3.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm3.setItemName("당뇨약");
        medicationInfoForm3.setEnptName("경기제약");

        MedicationInfoForm medicationInfoForm4 = new MedicationInfoForm();
        medicationInfoForm4.setDays(5);
        medicationInfoForm4.setMemo("먹어야함");
        medicationInfoForm4.setTablet(5);
        medicationInfoForm4.setEndDate(LocalDate.parse("2023-04-18"));
        medicationInfoForm4.setItemName("당뇨약");
        medicationInfoForm4.setEnptName("경기제약");

        MedicationInfo medicationInfo1 = new MedicationInfo(saveUser1, LocalDate.now(), medicationInfoForm1, Type.VitaminC);
        MedicationInfo medicationInfo2 = new MedicationInfo(saveUser1, LocalDate.now(), medicationInfoForm2, Type.Disabetes);
        MedicationInfo medicationInfo3 = new MedicationInfo(saveUser2, LocalDate.now(), medicationInfoForm2, Type.Disabetes);
        MedicationInfo medicationInfo4 = new MedicationInfo(saveUser2, LocalDate.now(), medicationInfoForm2, Type.Disabetes);

        medicationInfo1.firstState();
        medicationInfo2.firstState();
        medicationInfo3.lastState();
        medicationInfo4.lastState();

        MedicationInfo saveMedicationInfo1 = medicationInfoRepository.save(medicationInfo1);
        MedicationInfo saveMedicationInfo2 = medicationInfoRepository.save(medicationInfo2);
        MedicationInfo saveMedicationInfo3 = medicationInfoRepository.save(medicationInfo3);
        MedicationInfo saveMedicationInfo4 = medicationInfoRepository.save(medicationInfo4);


        medicationInfoRepository.flush();

        //when

        List<MedicationInfo> findInfo1 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndTypeIs(saveUser1.getUserId(), Type.Disabetes);
        List<MedicationInfo> findInfo2 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndTypeIs(saveUser1.getUserId(), Type.VitaminC);
        List<MedicationInfo> findInfo3 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndTypeIs(saveUser1.getUserId(), Type.Lutein);
        List<MedicationInfo> findInfo4 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndTypeIs(saveUser1.getUserId(), Type.VitaminD);
        List<MedicationInfo> findInfo5 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndTypeIs(saveUser2.getUserId(), Type.Disabetes);
        List<MedicationInfo> findInfo6 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndTypeIs(saveUser2.getUserId(), Type.VitaminC);
        List<MedicationInfo> findInfo7 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndTypeIs(saveUser2.getUserId(), Type.Lutein);
        List<MedicationInfo> findInfo8 = medicationInfoRepository.findMedicationInfoByUser_UserIdAndTypeIs(saveUser2.getUserId(), Type.VitaminD);

        //then

        assertThat(findInfo1.size()).isEqualTo(1);
        assertThat(findInfo2.size()).isEqualTo(1);
        assertThat(findInfo3.size()).isEqualTo(0);
        assertThat(findInfo4.size()).isEqualTo(0);
        assertThat(findInfo5.size()).isEqualTo(2);
        assertThat(findInfo6.size()).isEqualTo(0);
        assertThat(findInfo7.size()).isEqualTo(0);
        assertThat(findInfo8.size()).isEqualTo(0);
    }
}