package com.kgu.bravoHealthPark.domain.medicationInfo.repository;

import com.kgu.bravoHealthPark.domain.medicationInfo.domain.MedicationInfo;
import com.kgu.bravoHealthPark.domain.state.domain.State;
import com.kgu.bravoHealthPark.domain.type.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationInfoRepository extends JpaRepository<MedicationInfo,Long> {

    MedicationInfo findMedicationInfoByMedInfoId(Long medicationInfoId);
    List<MedicationInfo> findAllByUser_UserId(Long userId);
    List<MedicationInfo> findMedicationInfoByUser_UserIdAndStateIs(Long userId,State state);
    List<MedicationInfo> findMedicationInfoByUser_UserIdAndTypeIs(Long userId, Type type);
}
