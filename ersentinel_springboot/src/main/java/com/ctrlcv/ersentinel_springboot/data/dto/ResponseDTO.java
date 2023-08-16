package com.ctrlcv.ersentinel_springboot.data.dto;

import java.util.List;

import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    private boolean error;
    private List<T> data;
}
