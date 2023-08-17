package com.ctrlcv.ersentinel_springboot.data.dto;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoomSevereCapacityInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmergencyRoomSevereCapacityInfoDTO {
    private final List<String> ersclist = new ArrayList<>();
    private LocalDateTime updateTime;

    public EmergencyRoomSevereCapacityInfoDTO(final EmergencyRoomSevereCapacityInfo emergencyRoomSevereCapacityInfo){
        if(emergencyRoomSevereCapacityInfo.getMyocardialInfarction()){
            this.ersclist.add("심근경색");
        }
        if(emergencyRoomSevereCapacityInfo.getCerebralInfarction()){
            this.ersclist.add("뇌경색");
        }
        if(emergencyRoomSevereCapacityInfo.getSubarachnoidHemorrhage()){
            this.ersclist.add("뇌출혈[거미막하]");
        }
        if(emergencyRoomSevereCapacityInfo.getOtherBrainHemorrhage()){
            this.ersclist.add("뇌출혈[그 외]");
        }
        if(emergencyRoomSevereCapacityInfo.getThoracicAorta()){
            this.ersclist.add("대동맥 흉부");
        }
        if(emergencyRoomSevereCapacityInfo.getAbdominalAorta()){
            this.ersclist.add("대동맥 복부");
        }
        if(emergencyRoomSevereCapacityInfo.getGallbladderDisease()){
            this.ersclist.add("담낭질환");
        }
        if(emergencyRoomSevereCapacityInfo.getBileDuctDisease()){
            this.ersclist.add("담도포함질환");
        }
        if(emergencyRoomSevereCapacityInfo.getNonTraumaticAbdominalEmergency()){
            this.ersclist.add("복부응급 비외상");
        }
        if(emergencyRoomSevereCapacityInfo.getInfantIntestinalObstruction()){
            String temp = emergencyRoomSevereCapacityInfo.getInfantIntestinalAge();
            if(Objects.equals(temp, "Not Provided")){
                this.ersclist.add("장중첩 영유아");
            }else{
                this.ersclist.add("장중첩 영유아 ("+temp+")");
            }
        }
        if(emergencyRoomSevereCapacityInfo.getEmergencyGastrointestinalEndoscopy()){
            this.ersclist.add("위장관 응급내시경 성인");
        }
        if(emergencyRoomSevereCapacityInfo.getEmergencyGastrointestinalEndoscopyForChildren()){
            String temp = emergencyRoomSevereCapacityInfo.getGastrointestinalEndoscopyAge();
            if(Objects.equals(temp, "Not Provided")){
                this.ersclist.add("위장관 응급내시경 영유아");
            }else{
                this.ersclist.add("위장관 응급내시경 영유아 ("+temp+")");
            }
        }
        if(emergencyRoomSevereCapacityInfo.getEmergencyBronchoscopy()){
            this.ersclist.add("기관지 응급내시경 성인");
        }
        if(emergencyRoomSevereCapacityInfo.getEmergencyBronchoscopyForChildren()){
            String temp = emergencyRoomSevereCapacityInfo.getBronchoscopyAge();
            if(Objects.equals(temp, "Not Provided")){
                this.ersclist.add("기관지 응급내시경 영유아");
            }else{
                this.ersclist.add("기관지 응급내시경 영유아 ("+temp+")");
            }
        }
        if(emergencyRoomSevereCapacityInfo.getLowBirthWeightInfant()){
            String temp = emergencyRoomSevereCapacityInfo.getLowBirthWeightAge();
            if(Objects.equals(temp, "Not Provided")){
                this.ersclist.add("저출생체중아");
            }else{
                this.ersclist.add("저출생체중아 ("+temp+")");
            }
        }
        if(emergencyRoomSevereCapacityInfo.getObstetricDelivery()){
            this.ersclist.add("산부인과 분만");
        }
        if(emergencyRoomSevereCapacityInfo.getObstetricSurgery()){
            this.ersclist.add("산부인과 산과수술");
        }
        if(emergencyRoomSevereCapacityInfo.getEmergencyGynecologicalSurgery()){
            this.ersclist.add("산부인과응급 부인과수술");
        }
        if(emergencyRoomSevereCapacityInfo.getSevereBurns()){
            this.ersclist.add("중증화상");
        }
        if(emergencyRoomSevereCapacityInfo.getLimbReattachmentExtremities()){
            this.ersclist.add("사지접합 수족지접합");
        }
        if(emergencyRoomSevereCapacityInfo.getLimbReattachmentOther()){
            this.ersclist.add("사지접합 그외");
        }
        if(emergencyRoomSevereCapacityInfo.getEmergencyDialysisHD()){
            this.ersclist.add("응급투석 HD");
        }
        if(emergencyRoomSevereCapacityInfo.getEmergencyDialysisCRRT()){
            this.ersclist.add("응급투석 CRRT");
        }
        if(emergencyRoomSevereCapacityInfo.getPsychiatry()){
            this.ersclist.add("정신과");
        }
        if(emergencyRoomSevereCapacityInfo.getOphthalmicSurgery()){
            this.ersclist.add("안과적수술");
        }
        if(emergencyRoomSevereCapacityInfo.getRadiologyVascularIntervention()){
            this.ersclist.add("영상의학혈관중재 성인");
        }
        if(emergencyRoomSevereCapacityInfo.getRadiologyVascularInterventionForChildren()){
            String temp = emergencyRoomSevereCapacityInfo.getRadiologyAge();
            if(Objects.equals(temp, "Not Provided")){
                this.ersclist.add("영상의학혈관중재 영유아");
            }else{
                this.ersclist.add("영상의학혈관중재 영유아 ("+temp+")");
            }
        }




        this.updateTime = emergencyRoomSevereCapacityInfo.getUpdateTime();


    }


}
