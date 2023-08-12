package com.ctrlcv.ersentinel_springboot.data.dto;

import com.ctrlcv.ersentinel_springboot.data.entity.CongestionEntity;
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

    public CongestionDTO(final CongestionEntity entity){
        this.name = entity.name;
        if(entity.adultAvailableBeds == null || entity.adultStandardBeds == null){
            this.adultpercent = -1;
        }else{
            this.adultpercent = (entity.adultStandardBeds - entity.adultAvailableBeds) / entity.adultStandardBeds * 100;
        }
        if(entity.pediatricAvailableBeds == null || entity.pediatricStandardBeds == null){
            this.pediatricpercent = -1;
        }else{
            this.pediatricpercent = (entity.pediatricStandardBeds - entity.pediatricAvailableBeds) / entity.pediatricStandardBeds * 100;
        }
        this.longitude = entity.longitude;
        this.latitude = entity.latitude;
        this.phoneNumber = entity.phoneNumber;

    }


}
