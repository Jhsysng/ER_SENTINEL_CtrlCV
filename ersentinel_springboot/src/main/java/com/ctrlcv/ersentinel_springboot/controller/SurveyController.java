package com.ctrlcv.ersentinel_springboot.controller;

import com.ctrlcv.ersentinel_springboot.data.dto.*;
import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyMessage;
import com.ctrlcv.ersentinel_springboot.data.entity.HospitalEquipment;
import com.ctrlcv.ersentinel_springboot.data.entity.Survey;
import com.ctrlcv.ersentinel_springboot.service.SurveyService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/survey")
public class SurveyController {
    @Autowired
    private SurveyService service;


    @PostMapping
    public ResponseEntity<?> createSurvey(
            @RequestBody SurveyDTO dto) {
        try {

            String dutyId = dto.getDutyId();
            Survey entity = Survey.builder().hospital(service.retrieveHospitalByDutyId(dutyId).get())
                    .shortMessage(dto.getShortMessage())
                    .star(dto.getStar())
                    .build();

            // entity.setUserId(userId);

            Optional<Survey[]> objs = service.create(entity);

            if (objs.isEmpty()) {
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            Survey[] surveys = objs.get();
            if(surveys.length==0){
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }


            List<SurveyIdDTO> dtos = new ArrayList<>();

            for(Survey survey : surveys) {
                dtos.add(new SurveyIdDTO(survey));
            }

            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().data(dtos).build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.info(e.getMessage());
            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveSurvey(@RequestBody String dutyId)
    {
        try{
            log.info(dutyId);
            Optional<Survey[]> objs;
            objs = service.retrieveSurveyByDutyId(dutyId);
            if (objs.isEmpty()) {
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            Survey[] surveys = objs.get();
            if(surveys.length==0){
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }


            List<SurveyIdDTO> dtos = new ArrayList<>();

            for(Survey survey : surveys) {
                dtos.add(new SurveyIdDTO(survey));
            }

            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String error = e.getMessage();
            log.info(error);
            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PutMapping
    public ResponseEntity<?> updateTodo(
                                        @RequestBody SurveyIdDTO dto) {
        try{
            String dutyId = dto.getDutyId();
            Survey entity = Survey.builder().hospital(service.retrieveHospitalByDutyId(dutyId).get())
                    .shortMessage(dto.getShortMessage())
                    .star(dto.getStar())
                    .id(dto.getId())
                    .build();

            Optional<Survey[]> objs = service.update(entity);
            if (objs.isEmpty()) {
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            Survey[] surveys = objs.get();
            if(surveys.length==0){
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }


            List<SurveyIdDTO> dtos = new ArrayList<>();

            for(Survey survey : surveys) {
                dtos.add(new SurveyIdDTO(survey));
            }

            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error = e.getMessage();
            log.info(error);
            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(
            @RequestBody SurveyIdDTO dto) {
        try{
            String dutyId = dto.getDutyId();
            Survey entity = Survey.builder().hospital(service.retrieveHospitalByDutyId(dutyId).get())
                    .id(dto.getId())
                    .build();

            Optional<Survey[]> objs = service.delete(entity);
            if (objs.isEmpty()) {
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            Survey[] surveys = objs.get();
            if(surveys.length==0){
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }


            List<SurveyIdDTO> dtos = new ArrayList<>();

            for(Survey survey : surveys) {
                dtos.add(new SurveyIdDTO(survey));
            }

            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error = e.getMessage();
            log.info(error);
            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }







}


