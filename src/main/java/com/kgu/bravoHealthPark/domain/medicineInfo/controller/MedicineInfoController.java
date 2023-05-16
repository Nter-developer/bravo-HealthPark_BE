package com.kgu.bravoHealthPark.domain.medicineInfo.controller;

import com.kgu.bravoHealthPark.domain.medicineInfo.domain.MedicineInfo;
import com.kgu.bravoHealthPark.domain.medicineInfo.dto.MedicineInfoDto;
import com.kgu.bravoHealthPark.domain.medicineInfo.service.MedicineInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/medicineInfo")
public class MedicineInfoController {

    private final MedicineInfoService medicineInfoService;
    int pageNo=10;
    int numOfRows=100;

    @ApiOperation(value="받아온 data init")
    @PostMapping("/init")
    public ResponseEntity<List<MedicineInfoDto>> initData(){
        StringBuilder data =getApiValue();
        List<MedicineInfo> medicineInfoList = medicineInfoService.init(data.toString());
        List<MedicineInfoDto> medicineInfoDtos = medicineInfoList.stream()
                .map(MedicineInfoDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(medicineInfoDtos);
    }

    @ApiOperation(value = "id값으로 찾기")
    @GetMapping("/mediInfoId")
    public ResponseEntity<MedicineInfoDto> getInfoById(@RequestParam Long medicineInfoId){
        MedicineInfo medicineInfo = medicineInfoService.findByMedicineInfoId(medicineInfoId);
        MedicineInfoDto medicineInfoDto = new MedicineInfoDto(medicineInfo);
        return ResponseEntity.ok().body(medicineInfoDto);
    }

    @ApiOperation(value = "모든 정보 받기")
    @GetMapping("/all")
    public ResponseEntity<List<MedicineInfoDto>> getInfoAll(){
        List<MedicineInfo> medicineInfoList = medicineInfoService.findAll();
        List<MedicineInfoDto> medicineInfoDtoList = medicineInfoList.stream()
                .map(MedicineInfoDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(medicineInfoDtoList);
    }

    @ApiOperation(value = "약 이름으로 정보 찾기")
    @GetMapping("/itemName")
    public ResponseEntity<MedicineInfoDto> getInfoByItemName(@RequestParam String itemName){
        MedicineInfo medicineInfo = medicineInfoService.findByItemName(itemName);
        MedicineInfoDto medicineInfoDto = new MedicineInfoDto(medicineInfo);
        return ResponseEntity.ok().body(medicineInfoDto);
    }




    private static HttpURLConnection getHttpURLConnection(int pageNo, int numOfRows) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=e48ocdFPJPpHTBnLgTaGnMFZrj%2FnGlM0%2F41spHxHH%2BIGaVDyLZZmKRpoffGqjVinMbGRC%2BdaAKV4zwY1i9V34w%3D%3D");
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(Integer.toString(pageNo), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(Integer.toString(numOfRows), StandardCharsets.UTF_8));
        urlBuilder.append("&type=json");

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        return conn;
    }

    //응답 코드가 200 ~ 300 사이일 때 BufferReader를 통해서 값을 받아옴
    private static BufferedReader getBufferedReader(HttpURLConnection conn) throws IOException {
        BufferedReader bf;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            bf = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } else {
            bf = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        return bf;
    }

    //API 센터 값(String) 받아오기
    private StringBuilder getApiValue() {
        StringBuilder result = new StringBuilder();

        try {
            HttpURLConnection conn = getHttpURLConnection(pageNo, numOfRows);

            BufferedReader bf;
            bf = getBufferedReader(conn);

            String returnLine;
            while ((returnLine = bf.readLine()) != null) {
                result.append(returnLine);
            }

            bf.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @ApiOperation(value = "JSON Data 받아오기")
    @GetMapping
    public ResponseEntity callApiWithJson() {
        StringBuilder result = getApiValue();
        return ResponseEntity.ok().body(result);
    }
}
