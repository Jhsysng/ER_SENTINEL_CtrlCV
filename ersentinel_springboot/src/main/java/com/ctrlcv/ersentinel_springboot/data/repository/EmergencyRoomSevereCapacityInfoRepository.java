package com.ctrlcv.ersentinel_springboot.data.repository;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoomSevereCapacityInfo;
import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRoomSevereCapacityInfoRepository extends JpaRepository<EmergencyRoomSevereCapacityInfo, String> {
}
