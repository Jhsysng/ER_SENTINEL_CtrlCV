package com.ctrlcv.ersentinel_springboot.config.oauth.provider;

import com.ctrlcv.ersentinel_springboot.data.type.SocialType;

public interface Oauth2UserInfo {
    String getProviderId();
    SocialType getProvider();
    String getEmail();
    String getNickname();
}
