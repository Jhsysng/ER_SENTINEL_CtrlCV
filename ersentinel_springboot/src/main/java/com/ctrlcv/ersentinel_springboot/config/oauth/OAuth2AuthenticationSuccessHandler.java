package com.ctrlcv.ersentinel_springboot.config.oauth;

import com.ctrlcv.ersentinel_springboot.config.auth.PrincipalDetails;
import com.ctrlcv.ersentinel_springboot.data.dto.auth.TokenResponseDto;
import com.ctrlcv.ersentinel_springboot.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthService authService;

    public OAuth2AuthenticationSuccessHandler(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        TokenResponseDto tokenResponseDto = authService.generateTokens((PrincipalDetails) authentication.getPrincipal());
        String accessToken = tokenResponseDto.getAccessToken();
        String refreshToken = tokenResponseDto.getRefreshToken();

        response.sendRedirect(UriComponentsBuilder.fromUriString("http://localhost:3000/login/callback")
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString());
    }


}
