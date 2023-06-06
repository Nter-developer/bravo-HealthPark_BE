package com.kgu.bravoHealthPark.domain.calendar.repository;

import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import com.kgu.bravoHealthPark.domain.calendar.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    Calendar findByCalendarId(Long calendarId);

    Calendar findByAlarm_AlarmId(Long alarmId);
    List<Calendar> findAllByAlarm_MedicationInfo_User_UserId(Long userId);
    List<Calendar> findByAlarmStatusIs(AlarmStatus alarmStatus);
}
