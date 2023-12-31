package com.ctrlcv.ersentinel_springboot.data.repository;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoomSevereCapacityInfo;
import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmergencyRoomSevereCapacityInfoRepository extends JpaRepository<EmergencyRoomSevereCapacityInfo, String> {
    Optional<EmergencyRoomSevereCapacityInfo> findByHospitalDutyId(String dutyId);
}
