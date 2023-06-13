package com.kgu.bravoHealthPark.domain.calendar.service;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import com.kgu.bravoHealthPark.domain.calendar.domain.Calendar;
import com.kgu.bravoHealthPark.domain.calendar.repository.CalendarRepository;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;

    /**
     * 생성
     */
    @Transactional
    public Calendar save(Calendar calendar) {
        calendarRepository.save(calendar);
        return calendar;
    }

    /**
     * 삭제
     */
    @Transactional
    public void delete(Calendar calendar) {
        calendarRepository.delete(calendar);
    }

    /**
     * 검색
     */
    public Calendar findCalendarById(Long calendarId) {
        Calendar findCalendar = calendarRepository.findByCalendarId(calendarId);
        return findCalendar;
    }

    public Calendar findCalendarByAlarmId(Long alarmId) {
        Calendar findCalendar = calendarRepository.findByAlarm_AlarmId(alarmId);
        return findCalendar;
    }

    public List<Calendar> findCalendarAllByUser(Long loginId) {
        List<Calendar> all = calendarRepository.findAllByAlarm_MedicationInfo_User_LoginId(loginId);
        return all;
    }

    public List<Calendar> findCalendarByStatus(AlarmStatus alarmStatus) {
        List<Calendar> alarmStatusIs = calendarRepository.findByAlarmStatusIs(alarmStatus);
        return alarmStatusIs;
    }
}
