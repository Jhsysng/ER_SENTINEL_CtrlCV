package com.ctrlcv.ersentinel_springboot.config.auth;

import com.ctrlcv.ersentinel_springboot.data.entity.User;
import com.ctrlcv.ersentinel_springboot.data.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public PrincipalDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("해당 사용자 : {}를 찾을 수 없습니다. : ", username);
                    throw new EntityNotFoundException("해당 사용자를 찾을 수 없습니다. : " + username);
                });

        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }

        return null;
    }
}
