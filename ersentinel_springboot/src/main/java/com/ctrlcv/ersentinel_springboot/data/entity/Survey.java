package com.ctrlcv.ersentinel_springboot.data.entity;

import jakarta.persistence.*;
import lombok.*;

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

    private String shortMessage;

    private int Star;

    private LocalDateTime UploadTime;
    private LocalDateTime ModifyTime;



    @Builder
    public Survey(int id, Hospital hospital, String shortMessage, int Star, LocalDateTime UploadTime, LocalDateTime UpdateTime, LocalDateTime ModifyTime) {
        this.id = id;
        this.hospital = hospital;
        this.shortMessage = shortMessage;
        this.Star = Star;
        this.UploadTime = UploadTime;
        this.ModifyTime = ModifyTime;

    }
}
