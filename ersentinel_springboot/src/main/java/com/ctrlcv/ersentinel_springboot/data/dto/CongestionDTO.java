package com.ctrlcv.ersentinel_springboot.data.dto;

import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CongestionDTO {
    private String name;
    private int adultpercent;      // 혼잡도
    private int pediatricpercent;  // 혼잡도
    private String longitude;
    private String latitude;
    private String phoneNumber;    // 응급실 전번

    public CongestionDTO(final Hospital hospital, final EmergencyRoom emergencyRoom){
        this.name = hospital.getName();
        if(emergencyRoom.getAdultAvailableBeds() == -1 || emergencyRoom.getAdultStandardBeds() == -1){
        //TODO: 여기도 재욱이형한테 물어봐야함.
            this.adultpercent = -1;
        }else{
            this.adultpercent = (int)(((double) emergencyRoom.getAdultStandardBeds() - emergencyRoom.getAdultAvailableBeds()) / emergencyRoom.getAdultStandardBeds() * 100);
        }
        if(emergencyRoom.getPediatricAvailableBeds() == -1 || emergencyRoom.getPediatricStandardBeds() == -1){
            this.pediatricpercent = -1;
        }else{
            this.pediatricpercent = (int)(((double)emergencyRoom.getPediatricStandardBeds() - emergencyRoom.getPediatricAvailableBeds()) / emergencyRoom.getPediatricStandardBeds() * 100);
        }
        this.longitude = hospital.getLongitude();
        this.latitude = hospital.getLatitude();
        this.phoneNumber = hospital.getPhoneNumber();

    }


}
