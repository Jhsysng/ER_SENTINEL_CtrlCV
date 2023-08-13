package com.ctrlcv.ersentinel_springboot.service;

import com.ctrlcv.ersentinel_springboot.data.dto.distance.DistanceResponseDto;
import com.ctrlcv.ersentinel_springboot.data.repository.EmergencyRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDistanceService {

    private final EmergencyRoomRepository emergencyRoomRepository;

    private final StringBuilder urlBuilder
            = new StringBuilder("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytLcinfoInqire");

    @Value("${public-api.key}")
    private String key;

    @Autowired
    public UserDistanceService(EmergencyRoomRepository emergencyRoomRepository) {
        this.emergencyRoomRepository = emergencyRoomRepository;
    }
    /*
    public List<DistanceResponseDto> getHostpital(String longitude, String latitude) {



        ;
    }*/

}
