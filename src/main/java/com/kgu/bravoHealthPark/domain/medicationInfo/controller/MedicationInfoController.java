package com.kgu.bravoHealthPark.domain.medicationInfo.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoDto;
import com.kgu.bravoHealthPark.domain.state.domain.State;
//import com.kgu.bravoHealthPark.domain.type.domain.Type;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("medicationInfo")
public class MedicationInfoController {

    private final MedicationInfoService medicationInfoService;
    private final UserService userService;

    @PostMapping("/{userId}/{type}")
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

    @PostMapping("/image/{userId}")
    public String sendImageToPython(MultipartFile imageFile,@PathVariable Long userId) throws Exception {
        // Python 서버 URL 설정
        String pythonUrl = "http://127.0.0.1:5000/ocr";

        // HttpClient 생성
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(pythonUrl);

        // 파일 저장할 경로 설정
        String uploadPath = "C:\\Users\\user\\Desktop\\새 폴더 (22)";

        // MultipartFile을 File로 변환하여 저장
        String fileName = imageFile.getOriginalFilename();
        String filePath = uploadPath + File.separator + fileName;
        File convertedFile = new File(filePath);
        imageFile.transferTo(convertedFile);


        // MultipartEntityBuilder를 사용하여 사진 파일을 첨부
        HttpEntity multipartEntity = MultipartEntityBuilder.create()
                .addBinaryBody("image", convertedFile)
                .build();

        httpPost.setEntity(multipartEntity);

        // 요청 전송 및 응답 처리
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        String responseContent = EntityUtils.toString(responseEntity);
        User user = userService.findUserById(userId);
        processOcrResult(responseContent,user);

        return responseContent;
    }

    public void processOcrResult(String json, User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(json);

        if (jsonResponse.isArray()) { // JSON 응답이 배열인지 확인
            for (JsonNode objectNode : jsonResponse) {
                String name = objectNode.get("name").asText(); // name 속성 가져오기
                String num = objectNode.get("num").asText(); // num 속성 가져오기

                MedicationInfo medicationInfo = new MedicationInfo(user, LocalDate.now(), name, 1, 5, "아아아아아");
                medicationInfoService.save(medicationInfo);
            }
        }
//        JsonNode namesNode= jsonResponse.get("name");
//        List<String> nameList = new ArrayList<>();
//
//        if(namesNode.isArray()){
//            for(JsonNode nameNode :namesNode){
//                String name =nameNode.asText();
//                nameList.add(name);
//            }
//        }
//
//        for(String name:nameList){
//            MedicationInfo medicationInfo = new MedicationInfo(user, LocalDate.now(), name, 1, 5, "아아아아아");
//            medicationInfoService.save(medicationInfo);
//        }
    }
}