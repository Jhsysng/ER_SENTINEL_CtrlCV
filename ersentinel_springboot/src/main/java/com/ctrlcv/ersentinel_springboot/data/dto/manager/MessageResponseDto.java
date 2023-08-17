package com.ctrlcv.ersentinel_springboot.data.dto.manager;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyMessage;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageResponseDto {
    private String dutyId;
    private String emgMessage;
    private LocalDateTime emgMsgStartTime;
    private LocalDateTime emgMsgEndTime;
    private String emgMsgType;

    public MessageResponseDto(EmergencyMessage emergencyMessage) {
        this.dutyId = emergencyMessage.getHospital().getDutyId();
        this.emgMessage = emergencyMessage.getEmgMessage();
        this.emgMsgStartTime = emergencyMessage.getEmgMsgStartTime();
        this.emgMsgEndTime = emergencyMessage.getEmgMsgEndTime();
        this.emgMsgType = emergencyMessage.getEmgMsgType();
    }
}