package com.ctrlcv.ersentinel_springboot.data.entity;

import com.ctrlcv.ersentinel_springboot.data.type.EmergencyMsgType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmergencyMessage {

    @Id
    private String id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dutyId")
    private Hospital hospital;

    private String emgMessage;

    private EmergencyMsgType emgMsgType;
}
