package com.ctrlcv.ersentinel_springboot.config.oauth;

import com.ctrlcv.ersentinel_springboot.config.auth.PrincipalDetails;
import com.ctrlcv.ersentinel_springboot.config.oauth.provider.GoogleUserInfo;
import com.ctrlcv.ersentinel_springboot.config.oauth.provider.KakaoUserInfo;
import com.ctrlcv.ersentinel_springboot.config.oauth.provider.NaverUserInfo;
import com.ctrlcv.ersentinel_springboot.config.oauth.provider.Oauth2UserInfo;
import com.ctrlcv.ersentinel_springboot.data.entity.User;
import com.ctrlcv.ersentinel_springboot.data.repository.UserRepository;
import com.ctrlcv.ersentinel_springboot.data.type.SocialType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;

    public PrincipalOauth2UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration()); // registrationId로 어떤 OAuth로 로그인 했는지 확인가능.
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes(): " + oAuth2User.getAttributes());

        Oauth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            log.info("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            log.info("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response")) ;
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            log.info("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes()) ;
        }

        SocialType provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider.toString() + "_" + providerId;
        String password = bCryptPasswordEncoder.encode("TKCompany"); // @value로 바꾸기
        String email = oAuth2UserInfo.getEmail();
        String nickname = oAuth2UserInfo.getNickname();

        Optional<User> userEntity = userRepository.findByUsername(username);
        User user = null;

        if (userEntity.isEmpty()) {
            user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role("USER")
                    .build();
            userRepository.save(user);
        } else {
            user = userEntity.get();
        }
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}