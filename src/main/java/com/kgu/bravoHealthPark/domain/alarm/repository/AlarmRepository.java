package com.kgu.bravoHealthPark.domain.alarm.repository;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    Alarm findByAlarmId(Long alarmId);

    List<Alarm> findAll();
    List<Alarm> findByTitle(String title);
}
