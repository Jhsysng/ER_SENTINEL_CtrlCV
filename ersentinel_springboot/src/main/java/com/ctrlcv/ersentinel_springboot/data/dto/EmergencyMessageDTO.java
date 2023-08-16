package com.ctrlcv.ersentinel_springboot.data.dto;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmergencyMessageDTO {

    private String emgMessage;

    private LocalDateTime emgMsgStartTime;

    private LocalDateTime emgMsgEndTime;

    private String emgMsgType;



    public EmergencyMessageDTO(final EmergencyMessage emergencyMessage){
        this.emgMessage = emergencyMessage.getEmgMessage();
        this.emgMsgStartTime = emergencyMessage.getEmgMsgStartTime();
        this.emgMsgEndTime = emergencyMessage.getEmgMsgEndTime();;
        this.emgMsgType = emergencyMessage.getEmgMsgType();
    }


}
