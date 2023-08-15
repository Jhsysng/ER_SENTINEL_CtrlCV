package com.ctrlcv.ersentinel_springboot.data.dto;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoom;
import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import lombok.Getter;

@Getter
public class HospitalAndAvlBedDto {
    private String hospitalName;

    /**
     * 성인 가용 병상 -> 응급실 실시간 가용병상정보 조회 : 일반 (hvec)
     */
    private String pediatricStandardBeds;

    /**
     * 성인 기준 병상 -> 응급실 실시간 가용병상정보 조회 : 일반_기준 (HVS01)
     */
    private String adultStandardBeds;

    public HospitalAndAvlBedDto(Hospital hospital, EmergencyRoom emergencyRoom) {
        this.hospitalName = hospital.getName();
        this.pediatricStandardBeds = String.valueOf(emergencyRoom.getPediatricStandardBeds());
        this.adultStandardBeds = String.valueOf(emergencyRoom.getAdultStandardBeds());
    }

    public HospitalAndAvlBedDto(Hospital hospital) {
        this.hospitalName = hospital.getName();
        this.adultStandardBeds = "None";
        this.pediatricStandardBeds = "None";
    }
}
