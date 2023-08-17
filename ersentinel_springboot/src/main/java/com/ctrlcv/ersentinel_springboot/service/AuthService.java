package com.ctrlcv.ersentinel_springboot.service;

import com.ctrlcv.ersentinel_springboot.config.auth.PrincipalDetails;
import com.ctrlcv.ersentinel_springboot.config.jwt.TokenProvider;
import com.ctrlcv.ersentinel_springboot.data.dto.auth.TokenResponseDto;
import com.ctrlcv.ersentinel_springboot.data.entity.User;
import com.ctrlcv.ersentinel_springboot.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthService {
    @Value("${app.jwt.refreshToken.expiration.days}")
    private Long refreshTokenExpirationMinutes;

    private final RedisTemplate<String, String> redisTemplate;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public AuthService(RedisTemplate<String, String> redisTemplate, UserRepository userRepository, TokenProvider tokenProvider) {
        this.redisTemplate = redisTemplate;
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public void join(User user) {
        userRepository.save(user);
    }

    @Transactional
    public TokenResponseDto generateTokens (PrincipalDetails principalDetails)
 {
        String accessToken = tokenProvider.generateAccessToken(principalDetails);
        String refreshToken = tokenProvider.generateRefreshToken(principalDetails);

        // Redis에 저장 - 만료 시간 설정을 통해 자동 삭제 처리
        redisTemplate.opsForValue().set(
                principalDetails.getUsername(),
                refreshToken,
                refreshTokenExpirationMinutes,
                TimeUnit.MINUTES
        );

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public String getSavedRefreshToken(String username) {
        return redisTemplate.opsForValue().get(username);
    }

}
