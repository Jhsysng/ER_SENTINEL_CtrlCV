package com.ctrlcv.ersentinel_springboot.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmergencyRoom {
    @Id
    private String id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dutyId")
    private Hospital hospital;

    private String name;

    private String phoneNumber;

    /**
     * 소아 가용 병상
     */
    private int pediatricAvailableBeds;

    /**
     * 소아 기준 병상
     */
    private int pediatricStandardBeds;

    /**
     * 성인 가용 병상
     */
    private int adultAvailableBeds;

    /**
     * 성인 기준 병상
     */
    private int adultStandardBeds;

    private String emgMessage1;

    private String emgMessage2;

    private String emgMessage3;

    private LocalDateTime apiUpdateTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Builder
    public EmergencyRoom(Hospital hospital, String name, String phoneNumber, int pediatricAvailableBeds, int pediatricStandardBeds, int adultAvailableBeds, int adultStandardBeds, String emgMessage1, String emgMessage2, String emgMessage3, LocalDateTime apiUpdateTime, LocalDateTime updateTime) {
        this.hospital = hospital;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.pediatricAvailableBeds = pediatricAvailableBeds;
        this.pediatricStandardBeds = pediatricStandardBeds;
        this.adultAvailableBeds = adultAvailableBeds;
        this.adultStandardBeds = adultStandardBeds;
        this.emgMessage1 = emgMessage1;
        this.emgMessage2 = emgMessage2;
        this.emgMessage3 = emgMessage3;
        this.apiUpdateTime = apiUpdateTime;
        this.updateTime = updateTime;
    }
}
