package com.kgu.bravoHealthPark.domain.medicationInfo.repository;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.state.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationInfoRepository extends JpaRepository<MedicationInfo,Long> {

    MedicationInfo findMedicationInfoByMedInfoId(Long medicationInfoId);
    List<MedicationInfo> findAllByUser_LoginId(String loginId);
    List<MedicationInfo> findMedicationInfoByUser_LoginIdAndStateIs(String userId,State state);

    List<MedicationInfo> findMedicationInfoByItemNameAndUser_LoginId(String itemName,String loginId);
}
