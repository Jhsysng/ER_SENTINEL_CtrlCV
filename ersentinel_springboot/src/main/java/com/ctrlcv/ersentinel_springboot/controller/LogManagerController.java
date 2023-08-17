package com.ctrlcv.ersentinel_springboot.controller;

import com.ctrlcv.ersentinel_springboot.data.entity.Log;
import com.ctrlcv.ersentinel_springboot.service.LogManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/manager")
@Slf4j
public class LogManagerController {

    private LogManagerService logService;
    @GetMapping("/log")
    public ResponseEntity<List<Log>> getLog(){
        log.info("[getLog] called");
        return new ResponseEntity<>(logService.getLogs(), HttpStatus.OK);
    }

    @GetMapping("/serverlog")
    public ResponseEntity<List<String>> getServerLog() throws IOException {
        log.info("[getServerLog] called");
        List<String> lines = Files.readAllLines(Paths.get("/Users/alex/Library/CloudStorage/OneDrive-dongguk.edu/AssignmentProject/Project/securecode10th/ersentinel_springboot/src/main/java/com/ctrlcv/ersentinel_springboot/log/2023-08-18/server.log"));
        return new ResponseEntity<>(lines, HttpStatus.OK);
    }
}
