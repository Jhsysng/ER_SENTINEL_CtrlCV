package com.ctrlcv.ersentinel_springboot.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class EmergencyRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dutyId")
    private Hospital hospital;

    private String name;

    private String phoneNumber;

    /**
     * 소아 가용 병상 -> 응급실 실시간 가용병상정보 조회 : 소아 (hv28)
     */
    private int pediatricAvailableBeds;

    /**
     * 소아 기준 병상 -> 응급실 실시간 가용병상정보 조회 : 소아_기준 (HVS02)
     */
    private int pediatricStandardBeds;

    /**
     * 성인 가용 병상 -> 응급실 실시간 가용병상정보 조회 : 일반 (hvec)
     */
    private int adultAvailableBeds;

    /**
     * 성인 기준 병상 -> 응급실 실시간 가용병상정보 조회 : 일반_기준 (HVS01)
     */
    private int adultStandardBeds;

    private LocalDateTime apiUpdateTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Builder
    public EmergencyRoom(int id, Hospital hospital, String name, String phoneNumber, int pediatricAvailableBeds, int pediatricStandardBeds, int adultAvailableBeds, int adultStandardBeds, LocalDateTime apiUpdateTime, LocalDateTime updateTime) {
        this.id = id;
        this.hospital = hospital;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.pediatricAvailableBeds = pediatricAvailableBeds;
        this.pediatricStandardBeds = pediatricStandardBeds;
        this.adultAvailableBeds = adultAvailableBeds;
        this.adultStandardBeds = adultStandardBeds;
        this.apiUpdateTime = apiUpdateTime;
        this.updateTime = updateTime;
    }
}
