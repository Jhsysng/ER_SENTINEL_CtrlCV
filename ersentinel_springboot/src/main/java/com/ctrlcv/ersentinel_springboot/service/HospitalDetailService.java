package com.ctrlcv.ersentinel_springboot.service;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyMessage;
import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoom;
import com.ctrlcv.ersentinel_springboot.data.entity.HospitalEquipment;
import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoomSevereCapacityInfo;
import com.ctrlcv.ersentinel_springboot.data.repository.EmergencyRoomRepository;
import com.ctrlcv.ersentinel_springboot.data.repository.HospitalEquipmentRepository;
import com.ctrlcv.ersentinel_springboot.data.repository.EmergencyRoomSevereCapacityInfoRepository;
import com.ctrlcv.ersentinel_springboot.data.repository.EmergencyMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

@Service
public class HospitalDetailService {

;

    @Autowired
    private EmergencyRoomRepository emergencyRoomRepository;

    @Autowired
    private HospitalEquipmentRepository hospitalEquipmentRepository;

    @Autowired
    private EmergencyRoomSevereCapacityInfoRepository emergencyRoomSevereCapacityInfoRepository;

    @Autowired
    private EmergencyMessageRepository emergencyMessageRepository;

    public Optional<EmergencyRoom> retrieveEmgRoomByDutyId(final String dutyId){
        return emergencyRoomRepository.findByHospitalDutyId(dutyId);
    }

    public Optional<HospitalEquipment> retrieveHospitalEquipmentByDutyId(final String dutyId){
        return hospitalEquipmentRepository.findByHospitalDutyId(dutyId);
    }

    public Optional<EmergencyRoomSevereCapacityInfo> retrieveEmergencyRoomSevereCapacityInfoByDutyId(final String dutyId){
        return emergencyRoomSevereCapacityInfoRepository.findByHospitalDutyId(dutyId);
    }

    public Optional<EmergencyMessage[]> retrieveEmergencyMessageByDutyId(final String dutyId){
        return emergencyMessageRepository.findByHospitalDutyId(dutyId);
    }




}
