package com.ctrlcv.ersentinel_springboot.service;

import com.ctrlcv.ersentinel_springboot.data.entity.User;
import com.ctrlcv.ersentinel_springboot.data.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserManagerService {
    private final UserRepository userRepository;

    public UserManagerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUserList() {
        log.info("[UserManagerService] getAllUserList");
        return userRepository.findAll();
    }

    public void deleteUserByUserId(String username) {
        log.info("[UserManagerService] deleteUserByUserId"+username);
        userRepository.deleteByUsername(username);
    }
}
