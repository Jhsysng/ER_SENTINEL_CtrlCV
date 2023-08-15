package com.ctrlcv.ersentinel_springboot.service;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoom;
import com.ctrlcv.ersentinel_springboot.data.repository.EmergencyRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SelectByDistService {
    private final EmergencyRoomRepository emergencyRoomRepository;

    public SelectByDistService(EmergencyRoomRepository emergencyRoomRepository) {
        this.emergencyRoomRepository = emergencyRoomRepository;
    }

    @Transactional
    public EmergencyRoom getEmergencyRoomByHospital(String dutyId) {
        return emergencyRoomRepository.findByHospitalDutyId(dutyId).orElse(null);
    }
}
