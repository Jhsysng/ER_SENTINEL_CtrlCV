package com.ctrlcv.ersentinel_springboot.controller;

import com.ctrlcv.ersentinel_springboot.data.dto.HospitalAndAvlBedDto;
import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoom;
import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import com.ctrlcv.ersentinel_springboot.service.PublicDataApiService;
import com.ctrlcv.ersentinel_springboot.service.SelectByDistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class SelectByDistController {
    private final PublicDataApiService publicDataApiService;
    private final SelectByDistService selectByDistService;

    public SelectByDistController(PublicDataApiService publicDataApiService, SelectByDistService selectByDistService) {
        this.publicDataApiService = publicDataApiService;
        this.selectByDistService = selectByDistService;
    }


    /**
     * [
     *     {
     *         "hospitalName": "삼성서울병원",
     *         "pediatricStandardBeds": "4",
     *         "adultStandardBeds": "55"
     *     },
     *     {
     *         "hospitalName": "남양주한양병원",
     *         "pediatricStandardBeds": "-2147483648",
     *         "adultStandardBeds": "17"
     *     }
     * ]
     * @param params
     * {
     *     "lon" : "1"
     *     "lat" : "1",
     * }
     * @return 성인 기준 병상, 소아 기준 병상 정보를 포함한 병원 정보 리스트 / 빈 리스트 반환 가능 (-2147483648 : 정보 제공 안 함)
     */

    @PostMapping("/hospital/lonlat")
    public ResponseEntity<List<HospitalAndAvlBedDto>> getHospitalListByLatLon(@RequestBody Map<String, String> params) {
        double lon = Double.parseDouble(params.get("lon"));
        double lat = Double.parseDouble(params.get("lat"));

        List<HospitalAndAvlBedDto> hospitalAndAvlBedDtoList = publicDataApiService.getEmergencyDeptListInfoByLonLat(lon, lat).stream()
                .map(hospital -> {
                    EmergencyRoom emergencyRoom = selectByDistService.getEmergencyRoomByHospital(hospital.getDutyId());
                    if (emergencyRoom != null) {
                        return new HospitalAndAvlBedDto(hospital, emergencyRoom);
                    } else {
                        return new HospitalAndAvlBedDto(hospital);
                    }
                })
                .toList();


        return new ResponseEntity<>(hospitalAndAvlBedDtoList, HttpStatus.OK);
    }
}
