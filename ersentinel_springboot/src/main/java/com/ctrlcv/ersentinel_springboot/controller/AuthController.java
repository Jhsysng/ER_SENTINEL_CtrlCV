package com.ctrlcv.ersentinel_springboot.controller;

import com.ctrlcv.ersentinel_springboot.config.auth.PrincipalDetails;
import com.ctrlcv.ersentinel_springboot.config.jwt.TokenProvider;
import com.ctrlcv.ersentinel_springboot.data.dto.auth.LoginRequestDto;
import com.ctrlcv.ersentinel_springboot.data.dto.auth.TokenResponseDto;
import com.ctrlcv.ersentinel_springboot.data.entity.User;
import com.ctrlcv.ersentinel_springboot.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.Map;

@Slf4j
@RestController
public class AuthController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    public AuthController(BCryptPasswordEncoder bCryptPasswordEncoder, AuthService authService, TokenProvider tokenProvider, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

//    @PostMapping("/auth/signUp")
//    public ResponseEntity<String> signUp(@RequestBody Map<String, String> signUpRequest) {
//        String encPassword = bCryptPasswordEncoder.encode(signUpRequest.get("password"));
//        User user = User.builder()
//                .username(signUpRequest.get("username"))
//                .password(encPassword)
//                .email(signUpRequest.get("email"))
//                .role("USER")
//                .build();
//
//        authService.join(user);
//
//        return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
//    }


    /**
     * 로그인 요청을 받는 controller
     * 로그인 요청을 받으면 UsernamePasswordAuthenticationToken로 검증을 실행하고
     * SecurityContextHolder에 authentication을 저장 -> TokenProvider 클래스에서 토큰 생성시 저장된 정보를 claim에 담을 때 사용
     *
     * @param loginRequestDto
     * @return TokenResponseDto
     */
    @PostMapping("/manager/signIn")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getMnid(), loginRequestDto.getMnpw())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new ResponseEntity<TokenResponseDto>(authService.generateTokens((PrincipalDetails) authentication.getPrincipal()), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<String>("잘못된 비밀번호 입니다.", HttpStatus.UNAUTHORIZED);
        } catch (InternalAuthenticationServiceException e) {
            return new ResponseEntity<String>("잘못된 아이디 입니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * refresh 토큰을 통해서 Access 토큰과 refresh 토큰을 재발급 받음.
     *
     * @param tokenHeader
     * @return
     */
    @GetMapping("/auth/refreshToken")
    public ResponseEntity<?> regenerateToken(@RequestHeader("Authorization") String tokenHeader) {
        String refreshToken = tokenHeader.replace("Bearer ", "");
        String username = tokenProvider.getUsernameByToken(refreshToken);

        // 저장된 refresh 토큰과 사용자가 request 시 보낸 토큰과 일치하는지 확인
        if (!authService.getSavedRefreshToken(username).equals(refreshToken)) {
            log.info("잘못된 refresh 토큰입니다.");
            return new ResponseEntity<String>("잘못된 refresh 토큰입니다.", HttpStatus.UNAUTHORIZED);
        }

        PrincipalDetails principalDetails = (PrincipalDetails) userDetailsService.loadUserByUsername(username);
        TokenResponseDto tokenResponseDto = authService.generateTokens(principalDetails);
        return new ResponseEntity<TokenResponseDto>(tokenResponseDto, HttpStatus.OK);
    }
}
