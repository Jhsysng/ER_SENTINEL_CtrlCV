package com.ctrlcv.ersentinel_springboot.data.dto;

import com.ctrlcv.ersentinel_springboot.data.entity.HospitalEquipment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HospitalEquipmentDTO {
    private final List<String> eqlist = new ArrayList<>();
    private LocalDateTime updateTime;



    public HospitalEquipmentDTO(final HospitalEquipment hospitalEquipment){
        if(hospitalEquipment.getCT()){
            this.eqlist.add("CT");
        }
        if(hospitalEquipment.getMRI()){
            this.eqlist.add("MRI");
        }
        if(hospitalEquipment.getVentilator()){
            this.eqlist.add("인공호흡기");
        }
        if(hospitalEquipment.getVentilatorForChildren()){
            this.eqlist.add("인공호흡기조산아");
        }
        if(hospitalEquipment.getAngiographyMachine()){
            this.eqlist.add("혈관촬영기");
        }
        if(hospitalEquipment.getCRRT()){
            this.eqlist.add("CRRT");
        }
        if(hospitalEquipment.getECMO()){
            this.eqlist.add("ECMO");
        }
        if(hospitalEquipment.getHyperbaricOxygenTherapyUnit()){
            this.eqlist.add("고압산소치료기");
        }
        if(hospitalEquipment.getCentralTemperatureManagementCapable()){
            this.eqlist.add("중심체온기");
        }
        if(hospitalEquipment.getAmbulance()){
            this.eqlist.add("구급차");
        }
        this.updateTime = hospitalEquipment.getUpdateTime();


    }


}
