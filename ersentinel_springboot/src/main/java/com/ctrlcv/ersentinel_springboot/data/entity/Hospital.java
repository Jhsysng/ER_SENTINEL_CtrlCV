package com.ctrlcv.ersentinel_springboot.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital {
    @Id
    private String dutyId;

    private String name;

    private String phoneNumber;

    private String longitude;

    private String latitude;

    private String address;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "hospital")
    private List<EmergencyMessage> emergencyMessage = new ArrayList<EmergencyMessage>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "hospital")
    private EmergencyRoom emergencyRoom;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "hospital")
    private HospitalEquipment hospitalEquipment;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "hospital")
    private EmergencyRoomSevereCapacityInfo emergencyRoomSevereCapacityInfo;

    @Builder
    public Hospital(String dutyId, String name, String phoneNumber, String longitude, String latitude, String address, LocalDateTime updateTime) {
        this.dutyId = dutyId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.updateTime = updateTime;
    }
}
