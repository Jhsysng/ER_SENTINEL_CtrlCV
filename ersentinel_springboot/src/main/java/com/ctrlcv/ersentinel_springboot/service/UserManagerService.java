package com.ctrlcv.ersentinel_springboot.service;

import com.ctrlcv.ersentinel_springboot.data.entity.User;
import com.ctrlcv.ersentinel_springboot.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagerService {
    private final UserRepository userRepository;

    public UserManagerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<User> getAllUserList() {
        return userRepository.findAll();
    }

    @Transactional
    public void deleteUserByUserId(String un) {
        userRepository.deleteByUsername(un);
    }
}
