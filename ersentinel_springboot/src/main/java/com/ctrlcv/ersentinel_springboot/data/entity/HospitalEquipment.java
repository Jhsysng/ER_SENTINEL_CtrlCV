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
public class HospitalEquipment {
    @Id
    private String id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dutyId")
    private Hospital hospital;

    /**
     * CT
     */
    private boolean CT;

    /**
     * MRI
     */
    private boolean MRI;

    /**
     * 인공호흡기
     */
    private boolean ventilator;

    /**
     * 인공호흡기조산아
     */
    private boolean ventilatorForChildren;

    /**
     * 혈관촬영기
     */
    private boolean angiographyMachine;

    /**
     * 혈관촬영기조산아
     */
    private boolean angiographyMachineForChildren;

    /**
     * CRRT
     */
    private boolean CRRT;

    /**
     * ECMO
     */
    private boolean ECMO;

    /**
     * 고압산소치료기
     */
    private boolean hyperbaricOxygenTherapyUnit;

    /**
     * 중심체온기가능
     */
    private boolean centralTemperatureManagementCapable;

    /**
     * 구급차
     */
    private boolean ambulance;

    /**
     * 업데이트 시간
     */
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Builder
    public HospitalEquipment(Hospital hospital, boolean CT, boolean MRI, boolean ventilator, boolean ventilatorForChildren, boolean angiographyMachine, boolean angiographyMachineForChildren, boolean CRRT, boolean ECMO, boolean hyperbaricOxygenTherapyUnit, boolean centralTemperatureManagementCapable, boolean ambulance, LocalDateTime updateTime) {
        this.hospital = hospital;
        this.CT = CT;
        this.MRI = MRI;
        this.ventilator = ventilator;
        this.ventilatorForChildren = ventilatorForChildren;
        this.angiographyMachine = angiographyMachine;
        this.angiographyMachineForChildren = angiographyMachineForChildren;
        this.CRRT = CRRT;
        this.ECMO = ECMO;
        this.hyperbaricOxygenTherapyUnit = hyperbaricOxygenTherapyUnit;
        this.centralTemperatureManagementCapable = centralTemperatureManagementCapable;
        this.ambulance = ambulance;
        this.updateTime = updateTime;
    }
}
