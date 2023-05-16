package com.kgu.bravoHealthPark.domain.medicineInfo.repository;

import com.kgu.bravoHealthPark.domain.medicineInfo.domain.MedicineInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineInfoRepository extends JpaRepository<MedicineInfo,Long> {
    MedicineInfo findByItemName(String itemName);
    MedicineInfo findByMedicineInfoId(Long medicineInfoId);
}
