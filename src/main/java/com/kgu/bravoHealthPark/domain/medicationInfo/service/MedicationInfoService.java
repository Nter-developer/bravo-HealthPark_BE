package com.kgu.bravoHealthPark.domain.medicationInfo.service;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.medicationInfo.dto.MedicationInfoForm;
import com.kgu.bravoHealthPark.domain.medicationInfo.repository.MedicationInfoRepository;
import com.kgu.bravoHealthPark.domain.state.domain.State;
import com.kgu.bravoHealthPark.domain.type.domain.Type;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicationInfoService {

    private final MedicationInfoRepository medicationInfoRepository;

    public void save(MedicationInfo medicationInfo){
        medicationInfoRepository.save(medicationInfo);
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

    //약을 복용중 상태로 바꾸는거
    @Transactional
    public void startState(MedicationInfo medicationInfo){
        medicationInfo.firstState();
    }

    // 약을 복용완료 상태로 바꾸는거
    @Transactional
    public void endState(MedicationInfo medicationInfo){
        medicationInfo.lastState();
    }

    //복용상태로 받는거
    public List<MedicationInfo> findByState(Long userId,State state){
        return medicationInfoRepository.findMedicationInfoByUser_UserIdAndStateIs(userId,state);
    }

    @Transactional
    public void updateInfo(MedicationInfo medicationInfo,MedicationInfoForm medicationInfoForm){
        medicationInfo.updateInfo(medicationInfoForm.getEnptName(),medicationInfoForm.getItemName(),
                medicationInfoForm.getTablet(),medicationInfoForm.getStartDate(),medicationInfoForm.getEndDate());
    }

    // 복용상태 바꾸는거
    @Transactional
    public void changeState(MedicationInfo medicationInfo){
        medicationInfo.changeState();
    }

    @Transactional
    public void updateType(MedicationInfo medicationInfo, Type type){
        medicationInfo.updateType(type);
    }

    public List<MedicationInfo> findByType(Long userId,Type type){
        return medicationInfoRepository.findMedicationInfoByUser_UserIdAndTypeIs(userId,type);
    }
}
