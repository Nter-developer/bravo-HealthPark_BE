package com.kgu.bravoHealthPark.domain.medicationInfo.controller;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoDto;
import com.kgu.bravoHealthPark.domain.state.domain.State;
import com.kgu.bravoHealthPark.domain.type.domain.Type;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoForm;
import com.kgu.bravoHealthPark.domain.medicationInfo.service.MedicationInfoService;
import com.kgu.bravoHealthPark.domain.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("medicationInfo")
public class MedicationInfoController {

    private final MedicationInfoService medicationInfoService;
    private final UserService userService;

    @PostMapping("/{userId}/{type}")
    public ResponseEntity<MedicationInfoDto> save(
            @PathVariable Long userId,
            @RequestBody MedicationInfoForm medicationInfoForm,
            @PathVariable Type type){

        User user = userService.findByUser(userId);
        MedicationInfo medicationInfo = new MedicationInfo(user, LocalDate.now(),medicationInfoForm,type);
        medicationInfo.firstState();
        MedicationInfo saveMedicationInfo = medicationInfoService.save(medicationInfo);

        MedicationInfoDto medicationInfoDto = new MedicationInfoDto(saveMedicationInfo);
        return ResponseEntity.ok().body(medicationInfoDto);
    }

    @DeleteMapping("/{medicationInfoId}")
    public ResponseEntity delete(@PathVariable Long medicationInfoId){
        medicationInfoService.delete(medicationInfoId);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("")
    public ResponseEntity<MedicationInfoDto> findMedicationInfo(@RequestParam Long medicationInfoId){
        MedicationInfo medicationInfo = medicationInfoService.findByMedicationInfoId(medicationInfoId);

        MedicationInfoDto medicationInfoDto = new MedicationInfoDto(medicationInfo);

        return ResponseEntity.ok().body(medicationInfoDto);
    }

    @ApiOperation("모든 medication Info정보 받기")
    @GetMapping("/all")
    public ResponseEntity<List<MedicationInfoDto>> allMedicationInfo(@RequestParam Long userId){
        List<MedicationInfo> medicationInfos = medicationInfoService.findAllByUserId(userId);

        List<MedicationInfoDto> medicationInfoDtos = medicationInfos.stream()
                .map(MedicationInfoDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(medicationInfoDtos);
    }

    @ApiOperation("복용정보 수정")
    @PatchMapping("/{medicationInfoId}")
    public ResponseEntity<MedicationInfoDto> updateInfo(@PathVariable Long medicationInfoId,
                                                        @RequestBody MedicationInfoForm form){
        MedicationInfo medicationInfo = medicationInfoService.findByMedicationInfoId(medicationInfoId);
        medicationInfoService.updateInfo(medicationInfo,form);

        MedicationInfoDto medicationInfoDto = new MedicationInfoDto(medicationInfo);

        return ResponseEntity.ok().body(medicationInfoDto);
    }

    @ApiOperation("복용상태로 받는거")
    @GetMapping("/state/medicationInfo")
    public ResponseEntity<List<MedicationInfoDto>> getInfoByState(@RequestParam Long userId,
                                                                  @RequestParam State state){
        List<MedicationInfo> medicationInfos = medicationInfoService.findByState(userId, state);
        List<MedicationInfoDto> medicationInfoDtos = medicationInfos.stream()
                .map(MedicationInfoDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(medicationInfoDtos);
    }

    @ApiOperation("복용정보 현재상태에서 바꾸기")
    @PatchMapping("/state/{medicationInfoId}")
    public ResponseEntity<MedicationInfoDto> changeState(@PathVariable Long medicationInfoId){
        MedicationInfo medicationInfo = medicationInfoService.findByMedicationInfoId(medicationInfoId);
        medicationInfoService.changeState(medicationInfo);

        MedicationInfoDto medicationInfoDto = new MedicationInfoDto(medicationInfo);

        return ResponseEntity.ok().body(medicationInfoDto);
    }

    @ApiOperation("약 타입 바꾸는거")
    @PatchMapping("type/{userId}/{type}")
    public ResponseEntity<MedicationInfoDto> updateType(@PathVariable Long userId,
                                                        @PathVariable Type type){
        MedicationInfo medicationInfo = medicationInfoService.findByMedicationInfoId(userId);
        medicationInfoService.updateType(medicationInfo,type);

        MedicationInfoDto medicationInfoDto = new MedicationInfoDto(medicationInfo);

        return ResponseEntity.ok().body(medicationInfoDto);
    }

    @ApiOperation("약 타입으로 정보 받는거")
    @GetMapping("/type/medicationInfo")
    public ResponseEntity<List<MedicationInfoDto>> findByType(@RequestParam Long userId,
                                                        @RequestParam Type type){
        List<MedicationInfo> medicationInfos = medicationInfoService.findByType(userId, type);

        List<MedicationInfoDto> medicationInfoDtos = medicationInfos.stream()
                .map(MedicationInfoDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(medicationInfoDtos);
    }
}
