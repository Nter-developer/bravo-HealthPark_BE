package com.kgu.bravoHealthPark.domain.alarm.repository;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Alarm findByAlarmId(Long alarmId);
    List<Alarm> findByTitle(String title);
    List<Alarm> findByAlarmStatusIs(AlarmStatus alarmStatus);
    List<Alarm> findByMedicationInfo_User_UserId(Long userId);
    List<Alarm> findByMedicationInfo_User_UserIdAndAlarmStatus(Long userId, AlarmStatus alarmStatus);
}
