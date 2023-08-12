package com.ctrlcv.ersentinel_springboot.data.repository;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoom;
import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyRoomRepository extends JpaRepository<EmergencyRoom, String> {
}
