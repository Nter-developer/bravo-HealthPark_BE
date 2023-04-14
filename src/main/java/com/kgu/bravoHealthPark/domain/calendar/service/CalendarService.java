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
@Transactional
@RequiredArgsConstructor
public class CalendarService {
    private final CalendarRepository calendarRepository;

    /**
     * 생성
     */
    public Calendar save(Calendar calendar) {
        calendarRepository.save(calendar);
        return calendar;
    }

    /**
     * 삭제
     */
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

    public List<Calendar> findCalendarAll() {
        List<Calendar> all = calendarRepository.findAll();
        return all;
    }

    public List<Calendar> findCalendarByStatus(AlarmStatus alarmStatus) {
        List<Calendar> alarmStatusIs = calendarRepository.findByAlarmStatusIs(alarmStatus);
        return alarmStatusIs;
    }
}
