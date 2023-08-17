package com.ctrlcv.ersentinel_springboot.data.dto.manager;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HospitalResponseDto {
    private String dutyId;
    private String name;
    private String phoneNumber;
    private String longitude;
    private String latitude;
    private String address;
    private String firstAddress;
    private String secondAddress;
    private String updateTime;
}
