package com.ctrlcv.ersentinel_springboot.data.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
