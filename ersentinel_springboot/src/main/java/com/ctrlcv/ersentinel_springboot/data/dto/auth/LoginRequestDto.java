package com.ctrlcv.ersentinel_springboot.data.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    private String mnid;
    private String mnpw;
}
