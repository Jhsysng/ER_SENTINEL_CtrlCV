package com.ctrlcv.ersentinel_springboot.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmergencyRoomSevereCapacityInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dutyId")
    private Hospital hospital;

    /**
     * 심근경색
     */
    private Boolean myocardialInfarction;

    /**
     * 뇌경색
     */
    private Boolean cerebralInfarction;

    /**
     * 뇌출혈[거미막하]
     */
    private Boolean subarachnoidHemorrhage;

    /**
     * 뇌출혈[그 외]
     */
    private Boolean otherBrainHemorrhage;

    /**
     * 대동맥 흉부
     */
    private Boolean thoracicAorta;

    /**
     * 대동맥 복부
     */
    private Boolean abdominalAorta;

    /**
     * 담낭질환
     */
    private Boolean gallbladderDisease;

    /**
     * 담도포함질환
     */
    private Boolean bileDuctDisease;

    /**
     * 복부응급 비외상
     */
    private Boolean nonTraumaticAbdominalEmergency;

    /**
     * 장중첩/폐색 영유아
     */
    private Boolean infantIntestinalObstruction;

    /**
     * 위장관 응급내시경 성인
     */
    private Boolean emergencyGastrointestinalEndoscopy;

    /**
     * 위장관 응급내시경 영유아
     */
    private Boolean emergencyGastrointestinalEndoscopyForChildren;

    /**
     * 기관지 응급내시경 성인
     */
    private Boolean emergencyBronchoscopy;

    /**
     * 기관지 응급내시경 영유아
     */
    private Boolean emergencyBronchoscopyForChildren;

    /**
     * 저출생체중아
     */
    private Boolean lowBirthWeightInfant;

    /**
     * 산부인과 분만
     */
    private Boolean obstetricDelivery;

    /**
     * 산부인과 산과수술
     */
    private Boolean obstetricSurgery;

    /**
     * 산부인과응급 부인과수술
     */
    private Boolean emergencyGynecologicalSurgery;

    /**
     * 중증화상
     */
    private Boolean severeBurns;

    /**
     * 사지접합 수족지접합
     */
    private Boolean limbReattachmentExtremities;

    /**
     * 사지접합 그외
     */
    private Boolean limbReattachmentOther;

    /**
     * 응급투석 HD
     */
    private Boolean emergencyDialysisHD;

    /**
     * 응급투석 CRRT
     */
    private Boolean emergencyDialysisCRRT;

    /**
     * 정신과
     */
    private Boolean psychiatry;

    /**
     * 안과적수술
     */
    private Boolean ophthalmicSurgery;

    /**
     * 영상의학혈관중재 성인
     */
    private Boolean radiologyVascularIntervention;

    /**
     * 영상의학혈관중재 영유아
     */
    private Boolean radiologyVascularInterventionForChildren;

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
    public EmergencyRoomSevereCapacityInfo(int id, Hospital hospital, boolean myocardialInfarction, boolean cerebralInfarction, boolean subarachnoidHemorrhage, boolean otherBrainHemorrhage, boolean thoracicAorta, boolean abdominalAorta, boolean gallbladderDisease, boolean bileDuctDisease, boolean nonTraumaticAbdominalEmergency, boolean infantIntestinalObstruction, boolean emergencyGastrointestinalEndoscopy, boolean emergencyGastrointestinalEndoscopyForChildren, boolean emergencyBronchoscopy, boolean emergencyBronchoscopyForChildren, boolean lowBirthWeightInfant, boolean obstetricDelivery, boolean obstetricSurgery, boolean emergencyGynecologicalSurgery, boolean severeBurns, boolean limbReattachmentExtremities, boolean limbReattachmentOther, boolean emergencyDialysisHD, boolean emergencyDialysisCRRT, boolean psychiatry, boolean ophthalmicSurgery, boolean radiologyVascularIntervention, boolean radiologyVascularInterventionForChildren, String infantIntestinalAge, String gastrointestinalEndoscopyAge, String bronchoscopyAge, String lowBirthWeightAge, String radiologyAge, LocalDateTime updateTime) {
        this.id = id;
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
