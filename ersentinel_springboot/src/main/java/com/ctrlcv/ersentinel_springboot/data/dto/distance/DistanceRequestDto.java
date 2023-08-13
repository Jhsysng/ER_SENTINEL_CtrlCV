package com.ctrlcv.ersentinel_springboot.data.dto.distance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DistanceRequestDto {
    private String longitude;
    private String latitude;
}
