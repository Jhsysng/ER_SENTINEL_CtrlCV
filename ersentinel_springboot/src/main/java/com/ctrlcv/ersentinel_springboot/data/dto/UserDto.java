package com.ctrlcv.ersentinel_springboot.data.dto;

import com.ctrlcv.ersentinel_springboot.data.entity.User;
import com.ctrlcv.ersentinel_springboot.data.type.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private RoleType role;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
