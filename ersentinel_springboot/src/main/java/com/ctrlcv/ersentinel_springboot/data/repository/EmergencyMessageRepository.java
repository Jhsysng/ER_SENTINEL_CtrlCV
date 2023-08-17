package com.ctrlcv.ersentinel_springboot.data.repository;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyMessage;
import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmergencyMessageRepository extends JpaRepository<EmergencyMessage, Integer> {
    Optional<EmergencyMessage> findByEmgMessage(String emgMessage);
    Optional<EmergencyMessage[]> findByHospitalDutyId(String dutyId);
}
