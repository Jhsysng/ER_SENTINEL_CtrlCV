package com.ctrlcv.ersentinel_springboot.data.dto.manager;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ESevereResponseDto {
    private String dutyId;
    private Boolean myocardialInfarction;
    private Boolean cerebralInfarction;
    private Boolean subarachnoidHemorrhage;
    private Boolean otherBrainHemorrhage;
    private Boolean thoracicAorta;
    private Boolean abdominalAorta;
    private Boolean gallbladderDisease;
    private Boolean bileDuctDisease;
    private Boolean nonTraumaticAbdominalEmergency;
    private Boolean infantIntestinalObstruction;
    private Boolean emergencyGastrointestinalEndoscopy;
    private Boolean emergencyGastrointestinalEndoscopyForChildren;
    private Boolean emergencyBronchoscopy;
    private Boolean emergencyBronchoscopyForChildren;
    private Boolean lowBirthWeightInfant;
    private Boolean obstetricDelivery;
    private Boolean obstetricSurgery;
    private Boolean emergencyGynecologicalSurgery;
    private Boolean severeBurns;
    private Boolean limbReattachmentExtremities;
    private Boolean limbReattachmentOther;
    private Boolean emergencyDialysisHD;
    private Boolean emergencyDialysisCRRT;
    private Boolean psychiatry;
    private Boolean ophthalmicSurgery;
    private Boolean radiologyVascularIntervention;
    private Boolean radiologyVascularInterventionForChildren;
    private String infantIntestinalAge;
    private String gastrointestinalEndoscopyAge;
    private String bronchoscopyAge;
    private String lowBirthWeightAge;
    private String radiologyAge;
    private LocalDateTime updateTime;
}
