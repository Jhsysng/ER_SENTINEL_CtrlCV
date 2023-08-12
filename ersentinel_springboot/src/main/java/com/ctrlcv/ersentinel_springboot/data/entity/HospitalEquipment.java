package com.ctrlcv.ersentinel_springboot.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HospitalEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dutyId")
    private Hospital hospital;

    /**
     * CT
     */
    private Boolean CT;

    /**
     * MRI
     */
    private Boolean MRI;

    /**
     * 인공호흡기
     */
    private Boolean ventilator;

    /**
     * 인공호흡기조산아
     */
    private Boolean ventilatorForChildren;

    /**
     * 혈관촬영기
     */
    private Boolean angiographyMachine;


    /**
     * CRRT
     */
    private Boolean CRRT;

    /**
     * ECMO
     */
    private Boolean ECMO;

    /**
     * 고압산소치료기
     */
    private Boolean hyperbaricOxygenTherapyUnit;

    /**
     * 중심체온기가능
     */
    private Boolean centralTemperatureManagementCapable;

    /**
     * 구급차
     */
    private Boolean ambulance;

    /**
     * 업데이트 시간
     */
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Builder
    public HospitalEquipment(int id, Hospital hospital, Boolean CT, Boolean MRI, Boolean ventilator, Boolean ventilatorForChildren, Boolean angiographyMachine, Boolean CRRT, Boolean ECMO, Boolean hyperbaricOxygenTherapyUnit, Boolean centralTemperatureManagementCapable, Boolean ambulance, LocalDateTime updateTime) {
        this.id = id;
        this.hospital = hospital;
        this.CT = CT;
        this.MRI = MRI;
        this.ventilator = ventilator;
        this.ventilatorForChildren = ventilatorForChildren;
        this.angiographyMachine = angiographyMachine;
        this.CRRT = CRRT;
        this.ECMO = ECMO;
        this.hyperbaricOxygenTherapyUnit = hyperbaricOxygenTherapyUnit;
        this.centralTemperatureManagementCapable = centralTemperatureManagementCapable;
        this.ambulance = ambulance;
        this.updateTime = updateTime;
    }
}
