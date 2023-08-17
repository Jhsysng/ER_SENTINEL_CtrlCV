package com.ctrlcv.ersentinel_springboot.config;

import com.ctrlcv.ersentinel_springboot.config.jwt.JWTTokenAuthenticationFilter;
import com.ctrlcv.ersentinel_springboot.config.oauth.OAuth2AuthenticationSuccessHandler;
import com.ctrlcv.ersentinel_springboot.config.oauth.PrincipalOauth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    private final JWTTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    public SecurityConfig(PrincipalOauth2UserService principalOauth2UserService, JWTTokenAuthenticationFilter jwtTokenAuthenticationFilter, OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler) {
        this.principalOauth2UserService = principalOauth2UserService;
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // TODO: 잘 돌아가는지 확인해야 함
//                .authorizeHttpRequests((authorizeHttpRequests) ->
//                        authorizeHttpRequests
//                                .requestMatchers("/manager").hasRole("MANAGER")
//                                .requestMatchers("/**").hasRole("USER")
//                )


                .formLogin(formLogin ->
                        formLogin.loginPage("/loginForm")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/")
                                .failureUrl("/")
                )

                .oauth2Login(oauth2 ->
                        oauth2.loginPage("/loginForm")
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.userService(principalOauth2UserService)
                                )
                                .successHandler(oAuth2AuthenticationSuccessHandler)
                )

                .headers(headerConfig ->
                        headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                );

        return http.build();
    }
}
