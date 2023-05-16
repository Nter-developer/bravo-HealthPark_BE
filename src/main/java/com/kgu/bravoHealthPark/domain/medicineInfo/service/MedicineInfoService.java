package com.kgu.bravoHealthPark.domain.medicineInfo.service;

import com.kgu.bravoHealthPark.domain.medicineInfo.domain.MedicineInfo;
import com.kgu.bravoHealthPark.domain.medicineInfo.repository.MedicineInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MedicineInfoService {

    private final MedicineInfoRepository medicineInfoRepository;

    public List<MedicineInfo> init(String jsonData) {
        List<MedicineInfo> medicineInfoList = new ArrayList<>();
        try {
            JSONObject getObject;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
            JSONObject parseBody = (JSONObject) jsonObject.get("body");
            JSONArray items = (JSONArray) parseBody.get("items");

            for (Object object : items) {
                getObject = (JSONObject) object;

                MedicineInfo medicineInfo = new MedicineInfo((String) getObject.get("entpName"),
                        (String) getObject.get("itemName"),
                        (String) getObject.get("itemSeq"),
                        (String) getObject.get("efcyQesitm"),
                        (String) getObject.get("useMethodQesitm"),
                        (String) getObject.get("atpnWarnQesitm"),
                        (String) getObject.get("atpnQesitm"),
                        (String) getObject.get("intrcQesitm"),
                        (String) getObject.get("seQesitm"),
                        (String) getObject.get("depositMethodQesitm")
                );
                medicineInfoList.add(medicineInfo);
                medicineInfoRepository.save(medicineInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return medicineInfoList;
    }

    public MedicineInfo findByMedicineInfoId(Long medicineInfoId){
        return medicineInfoRepository.findByMedicineInfoId(medicineInfoId);
    }

    public List<MedicineInfo> findAll(){
        return medicineInfoRepository.findAll();
    }

    public MedicineInfo findByItemName(String itemName){
        return medicineInfoRepository.findByItemName(itemName);
    }
}
