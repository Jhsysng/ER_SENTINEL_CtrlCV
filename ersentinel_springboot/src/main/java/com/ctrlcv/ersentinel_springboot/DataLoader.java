package com.ctrlcv.ersentinel_springboot;

import com.ctrlcv.ersentinel_springboot.data.entity.User;
import com.ctrlcv.ersentinel_springboot.data.repository.UserRepository;
import com.ctrlcv.ersentinel_springboot.data.type.RoleType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${app.manager.username}")
    private String adun;

    @Value("${app.manager.password}")
    private String adpw;

    @Value("${app.manager.email}")
    private String adem;

    @Value("${app.manager.nickname}")
    private String adni;

    public DataLoader(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public void run (final String... args) throws Exception {
        if (userRepository.findByUsername(adun).isEmpty()) {
            User user = User.builder()
                    .username(adun)
                    .password(bCryptPasswordEncoder.encode(adpw))
                    .email(adem)
                    .nickname(adni)
                    .role(RoleType.ADMIN)
                    .build();

            userRepository.save(user);
        }
    }
}
