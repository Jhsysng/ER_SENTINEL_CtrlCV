package com.ctrlcv.ersentinel_springboot.data.dto.manager;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EquipmentResponseDto {
    private String dutyId;
    private Boolean CT;
    private Boolean MRI;
    private Boolean ventilator;
    private Boolean ventilatorForChildren;
    private Boolean angiographyMachine;
    private Boolean CRRT;
    private Boolean ECMO;
    private Boolean hyperbaricOxygenTherapyUnit;
    private Boolean centralTemperatureManagementCapable;
    private Boolean ambulance;
    private LocalDateTime updateTime;
}
