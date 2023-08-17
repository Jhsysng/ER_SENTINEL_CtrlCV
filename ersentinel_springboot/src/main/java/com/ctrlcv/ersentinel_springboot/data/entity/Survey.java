package com.ctrlcv.ersentinel_springboot.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dutyId")
    private Hospital hospital;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;

    private String shortMessage;

    private int star;

    @CreationTimestamp
    private LocalDateTime UploadTime;
    @UpdateTimestamp
    private LocalDateTime ModifyTime;



    @Builder
    public Survey(int id, Hospital hospital, String shortMessage, int star, LocalDateTime UploadTime, LocalDateTime UpdateTime, LocalDateTime ModifyTime, User user) {
        this.id = id;
        this.hospital = hospital;
        this.shortMessage = shortMessage;
        this.star = star;
        this.UploadTime = UploadTime;
        this.ModifyTime = ModifyTime;
        this.user = user;

    }
}