package com.ctrlcv.ersentinel_springboot.controller;

import com.ctrlcv.ersentinel_springboot.data.dto.*;
import com.ctrlcv.ersentinel_springboot.data.entity.*;
import com.ctrlcv.ersentinel_springboot.service.SurveyService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Objects;
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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            if (Objects.equals(username, "anonymousUser")){
                log.info("to create survey need to login");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }else if(username == null){
                log.info("name cannot be null");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            Optional<User> user = service.retrieveUserByUsername(username);
            if(user.isEmpty()) {
                log.info("cannot find user by name");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            String dutyId = dto.getDutyId();
            Optional<Hospital> hospital = service.retrieveHospitalByDutyId(dutyId);
            if(hospital.isEmpty()){
                log.info("cannot find hospital by dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            String shortmessage = dto.getShortMessage();
            if (shortmessage.length() > 100){ // TODO: 100자 미만으로 작성하라고 일러두기
                log.info("too long short message");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            int star = dto.getStar();
            if (star < 0 || star > 10){ // TODO: 만점 10점이라고 일러두기
                log.info("too small  or large star");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            Survey entity = Survey.builder().hospital(hospital.get())
                    .shortMessage(shortmessage)
                    .star(star)
                    .user(user.get())
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

    //응급실에 대한 survey를 다 가져옴
    @GetMapping
    public ResponseEntity<?> retrieveSurvey(@RequestBody String dutyId)
    {
        try{
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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String dutyId = dto.getDutyId();
            String username = authentication.getName();


            if (Objects.equals(username, "anonymousUser")){
                log.info("to update survey need to login");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }else if(username == null) {
                log.info("name cannot be null");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            Optional<Hospital> hospital = service.retrieveHospitalByDutyId(dutyId);
            if(hospital.isEmpty()){
                log.info("cannot find hospital by dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            Optional<Survey> survey = service.retrieveSurveyById(dto.getId());
            if(survey.isEmpty()) {
                log.info("cannot find survey by Id");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            User surveyuser = survey.get().getUser();   //TODO: 확인해야하나?

            if(!Objects.equals(surveyuser.getUsername(), username)){
                log.info("can not update other user's survey");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            int surveyid = dto.getId();
            if(service.retrieveSurveyById(surveyid).isEmpty()){
                log.info("can not find survey by id");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            String shortmessage = dto.getShortMessage();
            if (shortmessage.length() > 100){ // TODO: 100자 미만으로 작성하라고 일러두기
                log.info("too long short message");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            int star = dto.getStar();
            if (star < 0 || star > 10){ // TODO: 만점 10점이라고 일러두기
                log.info("too small  or large star");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            Survey entity = Survey.builder().hospital(hospital.get())
                    .shortMessage(shortmessage)
                    .star(star)
                    .id(surveyid)
                    .user(surveyuser)
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

            for(Survey onesurvey : surveys) {
                dtos.add(new SurveyIdDTO(onesurvey));
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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String dutyId = dto.getDutyId();
            String username = authentication.getName();


            if (Objects.equals(username, "anonymousUser")){
                log.info("to delete survey need to login");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }else if(username == null) {
                log.info("name cannot be null");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            Optional<Hospital> hospital = service.retrieveHospitalByDutyId(dutyId);
            if(hospital.isEmpty()){
                log.info("cannot find hospital by dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            Optional<Survey> survey = service.retrieveSurveyById(dto.getId());
            if(survey.isEmpty()) {
                log.info("cannot find survey by Id");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            User surveyuser = survey.get().getUser();   //TODO: 확인해야하나?

            if(!Objects.equals(surveyuser.getUsername(), username)){
                log.info("can not update other user's survey");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            int surveyid = dto.getId();
            if(service.retrieveSurveyById(surveyid).isEmpty()){
                log.info("can not find survey by id");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            Survey entity = Survey.builder().hospital(hospital.get())
                    .id(surveyid)
                    .build();

            Optional<Survey[]> objs = service.delete(entity);
            if (objs.isEmpty()) {
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            Survey[] surveys = objs.get();
            if(surveys.length==0){ // 정상적인 활동이나.. 보여줄게 없으므로 badrequest로 함.
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }


            List<SurveyIdDTO> dtos = new ArrayList<>();

            for(Survey onesurvey : surveys) {
                dtos.add(new SurveyIdDTO(onesurvey));
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


