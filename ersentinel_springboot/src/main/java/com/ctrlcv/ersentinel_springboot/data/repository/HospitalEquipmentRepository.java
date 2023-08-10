package com.ctrlcv.ersentinel_springboot.data.repository;

import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import com.ctrlcv.ersentinel_springboot.data.entity.HospitalEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalEquipmentRepository extends JpaRepository<HospitalEquipment, String> {
}
