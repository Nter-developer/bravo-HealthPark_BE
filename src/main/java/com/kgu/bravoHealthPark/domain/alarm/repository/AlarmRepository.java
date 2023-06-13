package com.kgu.bravoHealthPark.domain.alarm.repository;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Alarm findByAlarmId(Long alarmId);
    List<Alarm> findByAlarmStatusIs(AlarmStatus alarmStatus);
    List<Alarm> findByMedicationInfo_User_LoginId(String loginId);
    List<Alarm> findByMedicationInfo_User_LoginIdAndAlarmStatus(Long loginId, AlarmStatus alarmStatus);
}
