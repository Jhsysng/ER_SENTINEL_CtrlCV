package com.ctrlcv.ersentinel_springboot.data.dto;

import com.ctrlcv.ersentinel_springboot.data.entity.Survey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SurveyDTO {
    private int star;
    private String shortMessage;
    private String dutyId;

    public SurveyDTO(final Survey entity){

        //TODO : null 검사
        this.dutyId = entity.getHospital().getDutyId();
        this.star = entity.getStar();
        this.shortMessage = entity.getShortMessage();



    }
}
