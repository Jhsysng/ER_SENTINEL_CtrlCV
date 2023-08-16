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
public class CongestionInfoDTO {

    private int adultpercent;      // 혼잡도
    private int pediatricpercent;  // 혼잡도

    public CongestionInfoDTO(final EmergencyRoom emergencyRoom){

        if(emergencyRoom.getAdultAvailableBeds() == Integer.MIN_VALUE || emergencyRoom.getAdultStandardBeds() < 1){
            this.adultpercent = -1;
        }else{
            this.adultpercent = (int)(((double) emergencyRoom.getAdultStandardBeds() - emergencyRoom.getAdultAvailableBeds()) / emergencyRoom.getAdultStandardBeds() * 100);
        }
        if(emergencyRoom.getPediatricAvailableBeds() == Integer.MIN_VALUE || emergencyRoom.getPediatricStandardBeds() < 1){
            this.pediatricpercent = -1;
        }else{
            this.pediatricpercent = (int)(((double)emergencyRoom.getPediatricStandardBeds() - emergencyRoom.getPediatricAvailableBeds()) / emergencyRoom.getPediatricStandardBeds() * 100);
        }




    }


}
