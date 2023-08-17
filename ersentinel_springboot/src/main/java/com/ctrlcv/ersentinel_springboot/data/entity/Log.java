package com.ctrlcv.ersentinel_springboot.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Log extends Timestamped{
    @Id
    private Long id;

    @Column
    private String log;

    public Log(String log){
        this.log = log;
    }
}
