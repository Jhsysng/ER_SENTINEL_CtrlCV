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

    private String emgMsgType;

    private LocalDateTime lasttime;



    public EmergencyMessageDTO(final EmergencyMessage emergencyMessage, final LocalDateTime lasttime){
        this.emgMessage = emergencyMessage.getEmgMessage();
        this.emgMsgType = emergencyMessage.getEmgMsgType();
        this.lasttime = lasttime;
    }


}
