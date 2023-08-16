package com.ctrlcv.ersentinel_springboot.data.dto;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoadMapDTO {

    private String address;
    private String phoneNumber;



    public RoadMapDTO(final EmergencyRoom emergencyRoom){

        //TODO : null 검사
        this.address = emergencyRoom.getHospital().getAddress();
        this.phoneNumber = emergencyRoom.getPhoneNumber();



    }


}
