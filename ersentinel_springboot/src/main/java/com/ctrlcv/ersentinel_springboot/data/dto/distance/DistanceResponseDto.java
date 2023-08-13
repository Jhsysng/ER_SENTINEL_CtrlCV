package com.ctrlcv.ersentinel_springboot.data.dto.distance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DistanceResponseDto {
    private String longitude;
    private String latitude;
    private String hospitalName;
    private String hospitalCallNumber;
    private int pediatricCongestion;
    private int adultCongestion;
}
