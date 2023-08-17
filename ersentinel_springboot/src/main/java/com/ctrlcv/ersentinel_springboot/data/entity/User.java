package com.ctrlcv.ersentinel_springboot.data.entity;

import com.ctrlcv.ersentinel_springboot.data.type.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private RoleType role;

    @Builder
    public User(int id, String username, String password, String nickname, String email, RoleType role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }
}
