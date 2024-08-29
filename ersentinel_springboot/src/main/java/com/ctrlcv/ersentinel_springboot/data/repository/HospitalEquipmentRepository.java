package com.ctrlcv.ersentinel_springboot.data.repository;

import com.ctrlcv.ersentinel_springboot.data.entity.HospitalEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalEquipmentRepository extends JpaRepository<HospitalEquipment, String> {
    Optional<HospitalEquipment> findByHospitalDutyId(String dutyId);
}
