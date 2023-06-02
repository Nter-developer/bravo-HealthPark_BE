package com.kgu.bravoHealthPark.domain.medicationInfo.service;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoForm;
import com.kgu.bravoHealthPark.domain.medicationInfo.repository.MedicationInfoRepository;
import com.kgu.bravoHealthPark.domain.state.domain.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicationInfoService {

    private final MedicationInfoRepository medicationInfoRepository;

    public MedicationInfo save(MedicationInfo medicationInfo){
        return medicationInfoRepository.save(medicationInfo);
    }

    @Transactional
    public void delete(Long medicationInfoId){
        medicationInfoRepository.deleteById(medicationInfoId);
    }

    public MedicationInfo findByMedicationInfoId(Long medicationInfoId){
        return medicationInfoRepository.findMedicationInfoByMedInfoId(medicationInfoId);
    }

    public List<MedicationInfo> findAllByUserId(Long userId){
        return medicationInfoRepository.findAllByUser_UserId(userId);
    }

    //복용상태로 받는거
    public List<MedicationInfo> findByState(Long userId,State state){
        return medicationInfoRepository.findMedicationInfoByUser_UserIdAndStateIs(userId,state);
    }

    @Transactional
    public void updateInfo(MedicationInfo medicationInfo,MedicationInfoForm medicationInfoForm){
        medicationInfo.updateInfo(medicationInfoForm.getItemName(),
                medicationInfoForm.getTablet(),medicationInfoForm.getTimes(),medicationInfoForm.getDays(),medicationInfoForm.getMemo());
    }

    // 복용상태 바꾸는거
    @Transactional
    public void changeState(MedicationInfo medicationInfo){
        medicationInfo.changeState();
    }

    public List<MedicationInfo> findByItemNameAndUserId(String itemName,Long userId){
        return medicationInfoRepository.findMedicationInfoByItemNameAndUser_UserId(itemName,userId);
    }
}