package com.ctrlcv.ersentinel_springboot.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital {
    @Id
    private String dutyId;

    private String name;

    private String phoneNumber;

    private String longitude;

    private String latitude;

    private String address;

    private String firstAddress;

    private String secondAddress;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "hospital")
    private List<EmergencyMessage> emergencyMessage = new ArrayList<EmergencyMessage>();

    @Builder
    public Hospital(String dutyId, String name, String phoneNumber, String longitude, String latitude, String address, String firstAddress, String secondAddress, LocalDateTime updateTime, List<EmergencyMessage> emergencyMessage) {
        this.dutyId = dutyId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.firstAddress = firstAddress;
        this.secondAddress = secondAddress;
        this.updateTime = updateTime;
        this.emergencyMessage = emergencyMessage;
    }
}
