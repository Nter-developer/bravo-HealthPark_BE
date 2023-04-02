package com.kgu.bravoHealthPark.domain.alarm.controller;

import com.kgu.bravoHealthPark.domain.alarm.domain.Alarm;
import com.kgu.bravoHealthPark.domain.alarm.dto.AlarmDto;
import com.kgu.bravoHealthPark.domain.alarm.dto.AlarmForm;
import com.kgu.bravoHealthPark.domain.alarm.service.AlarmService;
import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"Alarm Api"}, description = "알람 관련 Api (#8)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;
//        private final MedicationInfoService medicationInfoService;

    @ApiOperation(value = "알람 생성")
    @PostMapping("/create")
    public ResponseEntity<Long> createAlarm(@RequestParam MedicationInfo medicationInfo,
//                                            @RequestParam Long medicationInfoId
                                            String title, LocalTime time) {
//        MedicationInfo medicationInfo = medicationInfoService.findByMedicationInfoId(medicationInfoId);
        Long alarmId = alarmService.alarm(medicationInfo, title, time);

        return ResponseEntity.ok().body(alarmId);
    }

    @ApiOperation(value = "알람 삭제")
    @DeleteMapping("/delete/{alarmId}")
    public ResponseEntity<AlarmDto> deleteAlarm(@PathVariable Long alarmId, HttpServletRequest request) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);
//        HttpSession session = request.getSession();
//        User loginUser = (User) session.getAttribute("loginUser");
//
//
//        if (alarmService.checkLoginId(findAlarm, loginUser)) {
//            alarmService.deleteAlarm(findAlarm);
//        } else {
//            System.out.println("로그인 정보가 일치하지 않아 삭제가 불가능합니다.");
//        }
        alarmService.deleteAlarm(findAlarm);
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "알람 수정")
    @PatchMapping("/update/{alarmId}")
    public ResponseEntity<AlarmDto> updateAlarm(@PathVariable Long alarmId, @RequestBody AlarmForm form, HttpServletRequest request) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);
//        HttpSession session = request.getSession();
//        User loginUser = (User) session.getAttribute("loginUser");
//
//        if (alarmService.checkLoginId(findAlarm, loginUser)) {
//            alarmService.updateAlarm(findAlarm, form);
//        } else {
//            System.out.println("로그인 정보가 일치하지 않아 변경이 불가능합니다.");
//        }
        alarmService.updateAlarm(findAlarm, form);
        AlarmDto alarmDto = new AlarmDto(findAlarm);

        return ResponseEntity.ok().body(alarmDto);
    }

    @ApiOperation(value = "알람 확인후 복용")
    @PatchMapping("/update/alarmstatus/dose/{alarmId}")
    public ResponseEntity<AlarmDto> updateAlarmStatusDose(@PathVariable Long alarmId, HttpServletRequest request) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);
//        HttpSession session = request.getSession();
//        User loginUser = (User) session.getAttribute("loginUser");
//
//        if (alarmService.checkLoginId(findAlarm, loginUser)) {
//            alarmService.changeAlarmDose(findAlarm);
//        } else {
//            System.out.println("로그인 정보가 일치하지 않아 변경이 불가능합니다.");
//        }
        alarmService.changeAlarmDose(findAlarm);
        AlarmDto alarmDto = new AlarmDto(findAlarm);

        return ResponseEntity.ok().body(alarmDto);
    }

    @ApiOperation(value = "알람 확인후 복용하지 않음")
    @PatchMapping("/update/alarmstatus/notdose/{alarmId}")
    public ResponseEntity<AlarmDto> updateUncheck(@PathVariable Long alarmId, HttpServletRequest request) {
        Alarm findAlarm = alarmService.findAlarmById(alarmId);
//        HttpSession session = request.getSession();
//        User loginUser = (User) session.getAttribute("loginUser");

//        if (alarmService.checkLoginId(findAlarm, loginUser)) {
//            alarmService.changeAlarmNotDose(findAlarm);
//        } else {
//            System.out.println("로그인 정보가 일치하지 않아 변경이 불가능합니다.");
//        }
        alarmService.changeAlarmNotDose(findAlarm);
        AlarmDto alarmDto = new AlarmDto(findAlarm);

        return ResponseEntity.ok().body(alarmDto);
    }

    @ApiOperation(value = "알람 Id로 찾기")
    @GetMapping("/search/{alarmId}")
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

    @ApiOperation(value = "약 이름으로 알람 찾기")
    @GetMapping("/search/name/{title}")
    public ResponseEntity<List<AlarmDto>> searchAlarmByTitle(@PathVariable String title) {
        List<Alarm> alarmList = alarmService.findAlarmByTitle(title);

        List<AlarmDto> result = alarmList.stream()
                .map(a -> new AlarmDto(a))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(result);
    }

}

