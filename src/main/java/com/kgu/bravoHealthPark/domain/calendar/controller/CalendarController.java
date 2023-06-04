package com.kgu.bravoHealthPark.domain.calendar.controller;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import com.kgu.bravoHealthPark.domain.alarm.service.AlarmService;
import com.kgu.bravoHealthPark.domain.calendar.domain.Calendar;
import com.kgu.bravoHealthPark.domain.calendar.dto.CalendarDto;
import com.kgu.bravoHealthPark.domain.calendar.service.CalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Api(tags = {"Calendar Api"}, description = "캘린더 관련 Api (#8)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
public class CalendarController {
    private final AlarmService alarmService;
    private final CalendarService calendarService;

    @ApiOperation(value = "캘린더 생성")
    @PostMapping("/{userId}")
    public ResponseEntity<List<CalendarDto>> createCalendar(@PathVariable Long userId) {
        List<Alarm> alarmList = alarmService.findAlarmByUser(userId);

        ArrayList<CalendarDto> calendarDtos = new ArrayList<>();

        for (Alarm alarm : alarmList) {
            Calendar calendar = new Calendar(alarm, alarm.getAlarmStatus(),
                    alarm.getDate().getYear(), alarm.getDate().getMonthValue(), alarm.getDate().getDayOfMonth());

            calendarService.save(calendar);
            CalendarDto calendarDto = new CalendarDto(calendar);
            calendarDtos.add(calendarDto);
        }

        return ResponseEntity.ok().body(calendarDtos);
    }

    @ApiOperation(value = "캘린더 삭제")
    @DeleteMapping("/{calendarId}")
    public ResponseEntity<?> deleteCalendar(@PathVariable Long calendarId) {
        Calendar findCalendar = calendarService.findCalendarById(calendarId);
        Alarm alarm = findCalendar.getAlarm();
        if (alarm != null) {
            findCalendar.deleteAlarm();
            calendarService.delete(findCalendar);
        }
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "캘린더 Id로 찾기")
    @GetMapping("")
    public ResponseEntity<CalendarDto> searchCalendarById(@RequestParam Long calendarId) {
        Calendar calendar = calendarService.findCalendarById(calendarId);

        CalendarDto calendarDto = new CalendarDto(calendar);

        return ResponseEntity.ok().body(calendarDto);
    }

    @ApiOperation(value = "캘린더 전체 찾기")
    @GetMapping("/all")
    public ResponseEntity<List<CalendarDto>> searchCalendarAll(@RequestParam Long userId) {
        List<Calendar> calendarList = calendarService.findCalendarAllByUser(userId);

        List<CalendarDto> result = calendarList.stream()
                .map(c -> new CalendarDto(c))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "복약 상태로 찾기")
    @GetMapping("/status")
    public ResponseEntity<List<CalendarDto>> searchCalendar(@RequestParam AlarmStatus alarmStatus) {
        List<Calendar> calendarByStatus = calendarService.findCalendarByStatus(alarmStatus);

        List<CalendarDto> result = calendarByStatus.stream()
                .map(c -> new CalendarDto(c))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

}
