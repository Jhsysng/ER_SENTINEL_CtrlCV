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
public class EmergencyRoomSevereCapacityInfo {
    @Id
    private String id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dutyId")
    private Hospital hospital;

    /**
     * 심근경색
     */
    private boolean myocardialInfarction;

    /**
     * 뇌경색
     */
    private boolean cerebralInfarction;

    /**
     * 뇌출혈[거미막하]
     */
    private boolean subarachnoidHemorrhage;

    /**
     * 뇌출혈[그 외]
     */
    private boolean otherBrainHemorrhage;

    /**
     * 대동맥 흉부
     */
    private boolean thoracicAorta;

    /**
     * 대동맥 복부
     */
    private boolean abdominalAorta;

    /**
     * 담낭질환
     */
    private boolean gallbladderDisease;

    /**
     * 담도포함질환
     */
    private boolean bileDuctDisease;

    /**
     * 복부응급 비외상
     */
    private boolean nonTraumaticAbdominalEmergency;

    /**
     * 장중첩/폐색 영유아
     */
    private boolean infantIntestinalObstruction;

    /**
     * 위장관 응급내시경 성인
     */
    private boolean emergencyGastrointestinalEndoscopy;

    /**
     * 위장관 응급내시경 영유아
     */
    private boolean emergencyGastrointestinalEndoscopyForChildren;

    /**
     * 기관지 응급내시경 성인
     */
    private boolean emergencyBronchoscopy;

    /**
     * 기관지 응급내시경 영유아
     */
    private boolean emergencyBronchoscopyForChildren;

    /**
     * 저출생체중아
     */
    private boolean lowBirthWeightInfant;

    /**
     * 산부인과 분만
     */
    private boolean obstetricDelivery;

    /**
     * 산부인과 산과수술
     */
    private boolean obstetricSurgery;

    /**
     * 산부인과응급 부인과수술
     */
    private boolean emergencyGynecologicalSurgery;

    /**
     * 중증화상
     */
    private boolean severeBurns;

    /**
     * 사지접합 수족지접합
     */
    private boolean limbReattachmentExtremities;

    /**
     * 사지접합 그외
     */
    private boolean limbReattachmentOther;

    /**
     * 응급투석 HD
     */
    private boolean emergencyDialysisHD;

    /**
     * 응급투석 CRRT
     */
    private boolean emergencyDialysisCRRT;

    /**
     * 정신과
     */
    private boolean psychiatry;

    /**
     * 안과적수술
     */
    private boolean ophthalmicSurgery;

    /**
     * 영상의학혈관중재 성인
     */
    private boolean radiologyVascularIntervention;

    /**
     * 영상의학혈관중재 영유아
     */
    private boolean radiologyVascularInterventionForChildren;

    /**
     * 장중첩영유아 연령
     */
    private String infantIntestinalAge;

    /**
     * 위장관내시경 연령
     */
    private String gastrointestinalEndoscopyAge;

    /**
     * 기관지내시경 연령
     */
    private String bronchoscopyAge;

    /**
     * 저출생 체중아 연령
     */
    private String lowBirthWeightAge;

    /**
     * 영상의학 연령
     */
    private String radiologyAge;

    /**
     * 업데이트 시간
     */
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Builder
    public EmergencyRoomSevereCapacityInfo(Hospital hospital, boolean myocardialInfarction, boolean cerebralInfarction, boolean subarachnoidHemorrhage, boolean otherBrainHemorrhage, boolean thoracicAorta, boolean abdominalAorta, boolean gallbladderDisease, boolean bileDuctDisease, boolean nonTraumaticAbdominalEmergency, boolean infantIntestinalObstruction, boolean emergencyGastrointestinalEndoscopy, boolean emergencyGastrointestinalEndoscopyForChildren, boolean emergencyBronchoscopy, boolean emergencyBronchoscopyForChildren, boolean lowBirthWeightInfant, boolean obstetricDelivery, boolean obstetricSurgery, boolean emergencyGynecologicalSurgery, boolean severeBurns, boolean limbReattachmentExtremities, boolean limbReattachmentOther, boolean emergencyDialysisHD, boolean emergencyDialysisCRRT, boolean psychiatry, boolean ophthalmicSurgery, boolean radiologyVascularIntervention, boolean radiologyVascularInterventionForChildren, String infantIntestinalAge, String gastrointestinalEndoscopyAge, String bronchoscopyAge, String lowBirthWeightAge, String radiologyAge, LocalDateTime updateTime) {
        this.hospital = hospital;
        this.myocardialInfarction = myocardialInfarction;
        this.cerebralInfarction = cerebralInfarction;
        this.subarachnoidHemorrhage = subarachnoidHemorrhage;
        this.otherBrainHemorrhage = otherBrainHemorrhage;
        this.thoracicAorta = thoracicAorta;
        this.abdominalAorta = abdominalAorta;
        this.gallbladderDisease = gallbladderDisease;
        this.bileDuctDisease = bileDuctDisease;
        this.nonTraumaticAbdominalEmergency = nonTraumaticAbdominalEmergency;
        this.infantIntestinalObstruction = infantIntestinalObstruction;
        this.emergencyGastrointestinalEndoscopy = emergencyGastrointestinalEndoscopy;
        this.emergencyGastrointestinalEndoscopyForChildren = emergencyGastrointestinalEndoscopyForChildren;
        this.emergencyBronchoscopy = emergencyBronchoscopy;
        this.emergencyBronchoscopyForChildren = emergencyBronchoscopyForChildren;
        this.lowBirthWeightInfant = lowBirthWeightInfant;
        this.obstetricDelivery = obstetricDelivery;
        this.obstetricSurgery = obstetricSurgery;
        this.emergencyGynecologicalSurgery = emergencyGynecologicalSurgery;
        this.severeBurns = severeBurns;
        this.limbReattachmentExtremities = limbReattachmentExtremities;
        this.limbReattachmentOther = limbReattachmentOther;
        this.emergencyDialysisHD = emergencyDialysisHD;
        this.emergencyDialysisCRRT = emergencyDialysisCRRT;
        this.psychiatry = psychiatry;
        this.ophthalmicSurgery = ophthalmicSurgery;
        this.radiologyVascularIntervention = radiologyVascularIntervention;
        this.radiologyVascularInterventionForChildren = radiologyVascularInterventionForChildren;
        this.infantIntestinalAge = infantIntestinalAge;
        this.gastrointestinalEndoscopyAge = gastrointestinalEndoscopyAge;
        this.bronchoscopyAge = bronchoscopyAge;
        this.lowBirthWeightAge = lowBirthWeightAge;
        this.radiologyAge = radiologyAge;
        this.updateTime = updateTime;
    }
}
