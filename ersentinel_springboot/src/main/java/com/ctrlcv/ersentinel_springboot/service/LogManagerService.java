package com.ctrlcv.ersentinel_springboot.service;

import com.ctrlcv.ersentinel_springboot.data.entity.Log;
import com.ctrlcv.ersentinel_springboot.data.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LogManagerService {

    @Autowired
    private LogRepository logRepository;

    public List<Log> getLogs(){
        log.info("[LogService] getLogs");
        return logRepository.findAll();
    }
}
