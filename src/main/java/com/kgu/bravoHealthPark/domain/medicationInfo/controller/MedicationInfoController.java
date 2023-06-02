package com.kgu.bravoHealthPark.domain.medicationInfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoDto;
import com.kgu.bravoHealthPark.domain.state.domain.State;
import com.kgu.bravoHealthPark.domain.user.domain.User;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoForm;
import com.kgu.bravoHealthPark.domain.medicationInfo.service.MedicationInfoService;
import com.kgu.bravoHealthPark.domain.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("medicationInfo")
public class MedicationInfoController {

    private final MedicationInfoService medicationInfoService;
    private final UserService userService;

    @PostMapping("/{userId}")
    public ResponseEntity<MedicationInfoDto> save(
            @PathVariable Long userId,
            @RequestBody MedicationInfoForm medicationInfoForm){

        User user = userService.findUserById(userId);
        MedicationInfo medicationInfo = new MedicationInfo(user, LocalDate.now(),medicationInfoForm);
        medicationInfo.firstState();
        MedicationInfo saveMedicationInfo = medicationInfoService.save(medicationInfo);

        MedicationInfoDto medicationInfoDto = new MedicationInfoDto(saveMedicationInfo);
        return ResponseEntity.ok().body(medicationInfoDto);
    }

    @DeleteMapping("/{medicationInfoId}")
    public ResponseEntity<?> delete(@PathVariable Long medicationInfoId){
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

    @ApiOperation("복용정보 현재상태에서 바꾸기")
    @PatchMapping("/state/{medicationInfoId}")
    public ResponseEntity<MedicationInfoDto> changeState(@PathVariable Long medicationInfoId){
        MedicationInfo medicationInfo = medicationInfoService.findByMedicationInfoId(medicationInfoId);
        medicationInfoService.changeState(medicationInfo);

        MedicationInfoDto medicationInfoDto = new MedicationInfoDto(medicationInfo);

        return ResponseEntity.ok().body(medicationInfoDto);
    }

    @PostMapping("/image/{userId}")
    public String sendImageToPython(@RequestPart MultipartFile imageFile,@PathVariable Long userId,String memo) throws Exception {
        // Python 서버 URL 설정
        String pythonUrl = "http://127.0.0.1:5000/ocr";

        // HttpClient 생성
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(pythonUrl);

        // 파일 저장할 경로 설정
        String uploadPath = "C:\\Users\\user\\Desktop\\새 폴더 (22)";

        String fileName = imageFile.getOriginalFilename();
        String filePath = uploadPath + File.separator + fileName;
        File convertedFile = new File(filePath);
        imageFile.transferTo(convertedFile);

        HttpEntity multipartEntity = MultipartEntityBuilder.create()
                .addBinaryBody("image", convertedFile)
                .build();

        httpPost.setEntity(multipartEntity);

        // 요청 전송 및 응답 처리
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String responseContent = EntityUtils.toString(responseEntity);
        User user = userService.findUserById(userId);
        processOcrResult(responseContent,user,memo);

        return responseContent;
    }

    public void processOcrResult(String json, User user,String memo) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(json);

        if (jsonResponse.isArray()) {
            for (JsonNode objectNode : jsonResponse) {
                String name = objectNode.get("name").asText();
                int tablet = objectNode.get("tablet").asInt();
                int times = objectNode.get("times").asInt();
                int days = objectNode.get("days").asInt();

                MedicationInfo medicationInfo = new MedicationInfo(user, LocalDate.now(), name,times,tablet,days, memo);
                medicationInfo.firstState();
                medicationInfoService.save(medicationInfo);
            }
        }
    }

    @ApiOperation("복용중인 약정보 받기")
    @GetMapping("/Doing")
    public ResponseEntity<List<MedicationInfoDto>> getInfoByDoing(@RequestParam Long userId){
        List<MedicationInfo> medicationInfos = medicationInfoService.findByState(userId, State.DOING);
        List<MedicationInfoDto> medicationInfoDtos = medicationInfos.stream()
                .map(MedicationInfoDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(medicationInfoDtos);
    }

    @ApiOperation("복용완료한 약정보 받기")
    @GetMapping("/Finished")
    public ResponseEntity<List<MedicationInfoDto>> getInfoByFinished(@RequestParam Long userId){
        List<MedicationInfo> medicationInfos = medicationInfoService.findByState(userId, State.FINISHED);
        List<MedicationInfoDto> medicationInfoDtos = medicationInfos.stream()
                .map(MedicationInfoDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(medicationInfoDtos);
    }

    @ApiOperation("약이름으로 정보 받기")
    @GetMapping("/item")
    public ResponseEntity<List<MedicationInfoDto>> getInfoByItemName(@RequestParam String itemName,@RequestParam Long userId){
        List<MedicationInfo> medicationInfoList = medicationInfoService.findByItemNameAndUserId(itemName,userId);
        List<MedicationInfoDto> medicationInfoDtos = medicationInfoList.stream()
                .map(MedicationInfoDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(medicationInfoDtos);
    }
}