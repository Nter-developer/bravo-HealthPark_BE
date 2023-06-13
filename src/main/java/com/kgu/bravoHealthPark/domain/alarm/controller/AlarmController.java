package com.kgu.bravoHealthPark.domain.alarm.controller;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import com.kgu.bravoHealthPark.domain.alarm.domain.Meal;
import com.kgu.bravoHealthPark.domain.alarm.dto.AlarmDto;
import com.kgu.bravoHealthPark.domain.alarm.dto.AlarmForm;
import com.kgu.bravoHealthPark.domain.alarm.service.AlarmService;
import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.medicationInfo.service.MedicationInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"Alarm Api"}, description = "알람 관련 Api (#8)")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;
    private final MedicationInfoService medicationInfoService;

    @ApiOperation(value = "알람 삭제")
    @DeleteMapping("/{alarmId}")
    public ResponseEntity<AlarmDto> deleteAlarm(@PathVariable Long alarmId) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);

        // 연관된 객체를 해제
        MedicationInfo medicationInfo = findAlarm.getMedicationInfo();
        if (medicationInfo != null) {
            findAlarm.deleteMedicationInfo();
            alarmService.deleteAlarm(findAlarm);
        }

        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "알람 수정")
    @PatchMapping("/update/{alarmId}")
    public ResponseEntity<AlarmDto> updateAlarm(@PathVariable Long alarmId, @RequestBody AlarmForm form) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);
        alarmService.updateAlarm(findAlarm, form);

        AlarmDto alarmDto = new AlarmDto(findAlarm);

        return ResponseEntity.ok().body(alarmDto);
    }

    @ApiOperation(value = "알람 확인후 복용 상태 변경")
    @PatchMapping("/update/dose/{alarmId}")
    public ResponseEntity<AlarmDto> updateAlarmStatusDose(@PathVariable Long alarmId) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);
        alarmService.changeAlarmDose(findAlarm);

        AlarmDto alarmDto = new AlarmDto(findAlarm);

        return ResponseEntity.ok().body(alarmDto);
    }

    @ApiOperation(value = "알람 확인후 복용하지 않음 상태로 변경")
    @PatchMapping("/update/notdose/{alarmId}")
    public ResponseEntity<AlarmDto> updateUncheck(@PathVariable Long alarmId) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);
        alarmService.changeAlarmNotDose(findAlarm);

        AlarmDto alarmDto = new AlarmDto(findAlarm);

        return ResponseEntity.ok().body(alarmDto);
    }

    @ApiOperation(value = "알람 Id로 찾기")
    @GetMapping("/{alarmId}")
    public ResponseEntity<AlarmDto> searchAlarmById(@PathVariable Long alarmId) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);

        AlarmDto result = new AlarmDto(findAlarm);

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "유저별 알람 찾기")
    @GetMapping("/{loginId}")
    public ResponseEntity<List<AlarmDto>> searchAlarmByUser(@PathVariable String loginId) {
        List<Alarm> alarmList = alarmService.findAlarmByLoginId(loginId);

        if (alarmList.isEmpty()) {
            log.debug("정보가 없습니다.");
        }

        List<AlarmDto> result = alarmList.stream()
                .map(a -> new AlarmDto(a))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "알람 상태로 알람 찾기")
    @GetMapping("/status")
    public ResponseEntity<List<AlarmDto>> searchAlarmByStatus(AlarmStatus alarmStatus) {
        List<Alarm> alarmList = alarmService.findAlarmByStatus(alarmStatus);

        if (alarmList.isEmpty()) {
            log.debug("정보가 없습니다.");
        }
        List<AlarmDto> result = alarmList.stream()
                .map(a -> new AlarmDto(a))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "유저별 알람 상태에 따라알람 찾기")
    @GetMapping("/status/{loginId}")
    public ResponseEntity<List<AlarmDto>> searchAlarmByUserAndStatus(@PathVariable Long loginId, AlarmStatus alarmStatus) {
        List<Alarm> alarmList = alarmService.findAlarmByUserAndStatus(loginId, alarmStatus);

        if (alarmList.isEmpty()) {
            log.debug("정보가 없습니다.");
        }

        List<AlarmDto> result = alarmList.stream()
                .map(a -> new AlarmDto(a))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

}

