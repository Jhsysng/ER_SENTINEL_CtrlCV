package com.ctrlcv.ersentinel_springboot.controller;

import com.ctrlcv.ersentinel_springboot.data.dto.UserDto;
import com.ctrlcv.ersentinel_springboot.service.UserManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserManagerController {

    private final UserManagerService userManagerService;

    public UserManagerController(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    @GetMapping("/manager/user")
    public ResponseEntity<?> getAllUser() {
        Map<String, List<UserDto>> body = new HashMap<>();

        List<UserDto> userDtoList = userManagerService.getAllUserList().stream()
                .map(UserDto::new)
                .toList();
        body.put("list",userDtoList);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/manager/user")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> unMap) {
        String un = unMap.get("username");
        userManagerService.deleteUserByUserId(un);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
}
