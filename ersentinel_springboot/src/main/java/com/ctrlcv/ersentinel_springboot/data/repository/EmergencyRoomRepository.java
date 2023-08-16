package com.ctrlcv.ersentinel_springboot.data.repository;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface EmergencyRoomRepository extends JpaRepository<EmergencyRoom, String> {
    Optional<EmergencyRoom> findByHospitalDutyId(String dutyId);


}
