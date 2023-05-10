package com.kgu.bravoHealthPark.domain.alarm.service;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import com.kgu.bravoHealthPark.domain.alarm.dto.AlarmForm;
import com.kgu.bravoHealthPark.domain.alarm.repository.AlarmRepository;
import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoForm;
import com.kgu.bravoHealthPark.domain.medicationInfo.repository.MedicationInfoRepository;
import com.kgu.bravoHealthPark.domain.medicationInfo.service.MedicationInfoService;
import com.kgu.bravoHealthPark.domain.type.domain.Type;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AlarmServiceTest {

    @Autowired
    private AlarmRepository alarmRepository;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private MedicationInfoRepository medicationInfoRepository;

    @Autowired
    private MedicationInfoService medicationInfoService;

    @Autowired
    private UserRepository userRepository;

    //Form으로 알람 속성들 변경 테스트
    @Test
    public void updateAlarmTest() {
        //given
        User user = User.builder()
                .phoneNumber("010-1234-5678")
                .name("홍길동")
                .build();

        User save = userRepository.save(user);

        MedicationInfoForm medicationInfoForm = new MedicationInfoForm();
        medicationInfoForm.setEnptName("test");
        medicationInfoForm.setDays(10);
        medicationInfoForm.setItemName("test");
        medicationInfoForm.setMemo("약");
        medicationInfoForm.setTablet(1);
        medicationInfoForm.setEndDate(LocalDate.now());

        MedicationInfo medicationInfo = new MedicationInfo(save, LocalDate.now(), medicationInfoForm, Type.VitaminC);
        medicationInfoRepository.save(medicationInfo);
        Alarm alarm = new Alarm(medicationInfo, "알람", LocalTime.of(12, 0));
        alarmRepository.save(alarm);

        AlarmForm form = new AlarmForm();
        form.setTitle("변경");
        form.setTime(LocalTime.of(11, 0));
        form.setStartDate(LocalDate.now());
        form.setEndDate(LocalDate.now());

        //when
        alarmService.updateAlarm(alarm, form);
        System.out.println(alarm.getTime());
        //then
        assertThat(alarm.getTitle()).isEqualTo("변경");
        assertThat(alarm.getTime()).isEqualTo(LocalTime.of(11, 0));
    }

    //알람 상태 변경 테스트
    @Test
    public void changAlarmStatusTest() {
        //given
        User user = User.builder()
                .phoneNumber("010-1234-5678")
                .name("홍길동")
                .build();

        User save = userRepository.save(user);

        MedicationInfoForm medicationInfoForm = new MedicationInfoForm();
        medicationInfoForm.setEnptName("test");
        medicationInfoForm.setDays(10);
        medicationInfoForm.setItemName("test");
        medicationInfoForm.setMemo("약");
        medicationInfoForm.setTablet(1);
        medicationInfoForm.setEndDate(LocalDate.now());

        MedicationInfo medicationInfo = new MedicationInfo(save,LocalDate.now(), medicationInfoForm, Type.VitaminC);
        medicationInfoService.save(medicationInfo);

        Alarm alarm1 = new Alarm(medicationInfo, "알람", LocalTime.of(12, 0));
        alarmRepository.save(alarm1);
        Alarm alarm2 = new Alarm(medicationInfo, "알람2", LocalTime.of(12, 0));
        alarmRepository.save(alarm2);
        //when
        alarmService.changeAlarmDose(alarm1);
        alarmService.changeAlarmNotDose(alarm2);

        //then
        assertThat(alarm1.getAlarmStatus()).isEqualTo(AlarmStatus.DOSE);
        assertThat(alarm2.getAlarmStatus()).isEqualTo(AlarmStatus.NOT_DOSE);
    }
}