package com.kgu.bravoHealthPark.domain.alarm.controller;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.domain.AlarmStatus;
import com.kgu.bravoHealthPark.domain.alarm.dto.AlarmDto;
import com.kgu.bravoHealthPark.domain.alarm.dto.AlarmForm;
import com.kgu.bravoHealthPark.domain.alarm.service.AlarmService;
import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.medicationInfo.service.MedicationInfoService;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"Alarm Api"}, description = "알람 관련 Api (#8)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;
    private final MedicationInfoService medicationInfoService;

    @ApiOperation(value = "알람 생성")
    @PostMapping("/create")
    public ResponseEntity<AlarmDto> createAlarm(Long medicationInfoId, String title, String time) {
        MedicationInfo medicationInfo = medicationInfoService.findByMedicationInfoId(medicationInfoId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(time, formatter);

        Alarm alarm = new Alarm(medicationInfo, title, localTime);
        alarm.initStatus();

        alarmService.save(alarm);

        AlarmDto alarmDto = new AlarmDto(alarm);

        return ResponseEntity.ok().body(alarmDto);
    }

    @ApiOperation(value = "알람 삭제")
    @DeleteMapping("/delete/{alarmId}")
    public ResponseEntity<AlarmDto> deleteAlarm(@PathVariable Long alarmId) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);
        alarmService.deleteAlarm(findAlarm);

        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "알람 수정")
    @PatchMapping("/update/{alarmId}")
    public ResponseEntity<AlarmDto> updateAlarm(@PathVariable Long alarmId, @RequestParam AlarmForm form) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);
        alarmService.updateAlarm(findAlarm, form);

        AlarmDto alarmDto = new AlarmDto(findAlarm);

        return ResponseEntity.ok().body(alarmDto);
    }

    @ApiOperation(value = "알람 확인후 복용 상태 ")
    @PatchMapping("/update/alarmstatus/dose/{alarmId}")
    public ResponseEntity<AlarmDto> updateAlarmStatusDose(@PathVariable Long alarmId) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);
        alarmService.changeAlarmDose(findAlarm);

        AlarmDto alarmDto = new AlarmDto(findAlarm);

        return ResponseEntity.ok().body(alarmDto);
    }

    @ApiOperation(value = "알람 확인후 복용하지 않음 상태")
    @PatchMapping("/update/alarmstatus/notdose/{alarmId}")
    public ResponseEntity<AlarmDto> updateUncheck(@PathVariable Long alarmId) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);
        alarmService.changeAlarmNotDose(findAlarm);

        AlarmDto alarmDto = new AlarmDto(findAlarm);

        return ResponseEntity.ok().body(alarmDto);
    }

    @ApiOperation(value = "알람 Id로 찾기")
    @GetMapping("/search/alarm/{alarmId}")
    public ResponseEntity<AlarmDto> searchAlarmById(@PathVariable Long alarmId) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);

        AlarmDto result = new AlarmDto(findAlarm);

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "알람 전체 찾기")
    @GetMapping("/all")
    public ResponseEntity<List<AlarmDto>> searchAllAlarm() {
        List<Alarm> alarmList = alarmService.findAlarmAll();

        List<AlarmDto> result = alarmList.stream()
                .map(a -> new AlarmDto(a))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "알람 이름으로 알람 찾기")
    @GetMapping("/search/title")
    public ResponseEntity<List<AlarmDto>> searchAlarmByTitle(@RequestParam String title) {
        List<Alarm> alarmList = alarmService.findAlarmByTitle(title);

        List<AlarmDto> result = alarmList.stream()
                .map(a -> new AlarmDto(a))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "알람 상태로 알람 찾기")
    @GetMapping("/search/status")
    public ResponseEntity<List<AlarmDto>> searchAlarmByStatus(@RequestParam AlarmStatus alarmStatus) {
        List<Alarm> alarmList = alarmService.findAlarmByStatus(alarmStatus);

        List<AlarmDto> result = alarmList.stream()
                .map(a -> new AlarmDto(a))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "유저별 알람 찾기")
    @GetMapping("/search/user/{userId}")
    public ResponseEntity<List<AlarmDto>> searchAlarmByUser(@PathVariable Long userId) {
        List<Alarm> alarmList = alarmService.findAlarmByUser(userId);

        List<AlarmDto> result = alarmList.stream()
                .map(a -> new AlarmDto(a))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "유저별 알람 상태에 따라알람 찾기")
    @GetMapping("/search/user/status/{userId}")
    public ResponseEntity<List<AlarmDto>> searchAlarmByUserAndStatus(@PathVariable Long userId, @RequestParam AlarmStatus alarmStatus) {
        List<Alarm> alarmList = alarmService.findAlarmByUserAndStatus(userId, alarmStatus);

        List<AlarmDto> result = alarmList.stream()
                .map(a -> new AlarmDto(a))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

}

