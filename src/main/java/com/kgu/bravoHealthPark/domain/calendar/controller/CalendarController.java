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
    @PostMapping("/create")
    public ResponseEntity<CalendarDto> createCalendar(Long alarmId, int year, int month, int day) {
        Alarm alarm = alarmService.findAlarmById(alarmId);
        Calendar calendar = new Calendar(alarm, alarm.getAlarmStatus(), year, month, day);

        calendarService.save(calendar);
        CalendarDto calendarDto = new CalendarDto(calendar);

        return ResponseEntity.ok().body(calendarDto);
    }

    @ApiOperation(value = "캘린더 삭제")
    @DeleteMapping("/create")
    public ResponseEntity createCalendar(@PathVariable Long calendarId) {
        Calendar findCalendar = calendarService.findCalendarById(calendarId);
        calendarService.delete(findCalendar);

        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "캘린더 Id로 찾기")
    @GetMapping("/search/{calendarId}")
    public ResponseEntity<CalendarDto> searchCalendarById(@PathVariable Long calendarId) {
        Calendar calendar = calendarService.findCalendarById(calendarId);

        CalendarDto calendarDto = new CalendarDto(calendar);

        return ResponseEntity.ok().body(calendarDto);
    }

    @ApiOperation(value = "캘린더 전체 찾기")
    @GetMapping("/search/all")
    public ResponseEntity<List<CalendarDto>> searchCalendarAll() {
        List<Calendar> calendarList = calendarService.findCalendarAll();

        List<CalendarDto> result = calendarList.stream()
                .map(c -> new CalendarDto(c))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "복약 상태로 찾기")
    @GetMapping("/search/status")
    public ResponseEntity<List<CalendarDto>> searchCalendar(@RequestParam AlarmStatus alarmStatus) {
        List<Calendar> calendarByStatus = calendarService.findCalendarByStatus(alarmStatus);

        List<CalendarDto> result = calendarByStatus.stream()
                .map(c -> new CalendarDto(c))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

}
