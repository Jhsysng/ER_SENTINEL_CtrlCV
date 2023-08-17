package com.ctrlcv.ersentinel_springboot.controller;

import com.ctrlcv.ersentinel_springboot.data.dto.*;
import com.ctrlcv.ersentinel_springboot.data.entity.*;
import com.ctrlcv.ersentinel_springboot.data.repository.SurveyRepository;
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

    private final SurveyRepository surveyRepository;

    public SurveyController(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @PostMapping
    public ResponseEntity<?> createSurvey(
            @RequestBody SurveyDTO dto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            log.info("username is" + username);
            if (Objects.equals(username, "anonymousUser")){
                log.error("to create survey need to login");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            log.info("username is valid but don't know yet!! he is owner of survey");

            Optional<User> user = service.retrieveUserByUsername(username);
            if(user.isEmpty()) {
                log.info("cannot find user by name");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            String dutyId = dto.getDutyId();
            log.info(dutyId);
            Optional<Hospital> hospital = service.retrieveHospitalByDutyId(dutyId);
            if(hospital.isEmpty()){
                log.error("cannot find hospital by dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            String shortmessage = dto.getShortMessage();
            if (shortmessage.length() > 100){ // TODO: 100자 미만으로 작성하라고 일러두기
                log.error("too long short message");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            log.info("shortmessage is valid");


            int star = dto.getStar();
            if (star < 0 || star > 10){ // TODO: 만점 10점이라고 일러두기
                log.error("too small  or large star");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            log.info("star is valid");


            Survey entity = Survey.builder().hospital(hospital.get())
                    .shortMessage(shortmessage)
                    .star(star)
                    .user(user.get())
                    .build();

            // entity.setUserId(userId);

            Optional<Survey[]> objs = service.create(entity);

            if (objs.isEmpty()) {
                log.error("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            Survey[] surveys = objs.get();
            if(surveys.length==0){
                log.error("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }


            List<SurveyIdDTO> dtos = new ArrayList<>();
            for(Survey survey : surveys) {
                dtos.add(new SurveyIdDTO(survey));
            }

            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().data(dtos).build();
            log.info("create success!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    //응급실에 대한 survey를 다 가져옴
    @GetMapping
    public ResponseEntity<?> retrieveSurvey(@RequestParam String dutyId)
    {
        try{
            log.info("dutyId" + dutyId);
            Optional<Survey[]> objs;

            objs = service.retrieveSurveyByDutyId(dutyId);
            if (objs.isEmpty()) {
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).data(new ArrayList<>()).build();
                return ResponseEntity.ok().body(response);
            }

            Survey[] surveys = objs.get();
            if(surveys.length==0){
                log.info("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).data(new ArrayList<>()).build();
                return ResponseEntity.ok().body(response);
            }

            log.info("there is at least one survey");

            List<SurveyIdDTO> dtos = new ArrayList<>();

            for(Survey survey : surveys) {
                dtos.add(new SurveyIdDTO(survey));
            }

            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().data(dtos).build();
            log.info("retrieve success!");
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String error = e.getMessage();
            log.error(error);
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
                log.error("to update survey need to login");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }else if(username == null) {
                log.error("name cannot be null");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            log.info("username is valid but don't know he is owner of survey");

            Optional<Hospital> hospital = service.retrieveHospitalByDutyId(dutyId);
            if(hospital.isEmpty()){
                log.error("cannot find hospital by dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            Optional<Survey> survey = service.retrieveSurveyById(dto.getId());
            if(survey.isEmpty()) {
                log.error("cannot find survey by Id");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            User surveyuser = survey.get().getUser();   //TODO: 확인해야하나?

            if(!Objects.equals(surveyuser.getUsername(), username)){
                log.error("can not update other user's survey");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            log.info("username is same with owner's name");

            int surveyid = dto.getId();
            if(service.retrieveSurveyById(surveyid).isEmpty()){
                log.error("can not find survey by id");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            String shortmessage = dto.getShortMessage();
            if (shortmessage.length() > 100){ // TODO: 100자 미만으로 작성하라고 일러두기
                log.error("too long short message");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            log.info("shortmessage is valid");

            int star = dto.getStar();
            if (star < 0 || star > 10){ // TODO: 만점 10점이라고 일러두기
                log.info("too small  or large star");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            log.info("star is valid");

            Survey entity = Survey.builder().hospital(hospital.get())
                    .shortMessage(shortmessage)
                    .star(star)
                    .id(surveyid)
                    .user(surveyuser)
                    .build();

            Optional<Survey[]> objs = service.update(entity);
            if (objs.isEmpty()) {
                log.error("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            Survey[] surveys = objs.get();
            if(surveys.length==0){
                log.error("there is no surveys matching dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }


            List<SurveyIdDTO> dtos = new ArrayList<>();

            for(Survey onesurvey : surveys) {
                dtos.add(new SurveyIdDTO(onesurvey));
            }

            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().data(dtos).build();
            log.info("update success!");
            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error = e.getMessage();
            log.error(error);
            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/d")
    public ResponseEntity<?> deleteTodo(
            @RequestBody SurveyIdDTO dto) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String dutyId = dto.getDutyId();
            String username = authentication.getName();

            if (Objects.equals(username, "anonymousUser")){
                log.error("to delete survey need to login");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            log.info("username is valid but don't know he is owner of survey");

            Optional<Hospital> hospital = service.retrieveHospitalByDutyId(dutyId);
            if(hospital.isEmpty()){
                log.error("cannot find hospital by dutyId");
                ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            log.info(dto.getDutyId());
            log.info(dto.getShortMessage());
            service.deleteSurveyByDutyId(dto);
            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().data(new ArrayList<>()).build();
            log.info("delete success!");
            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error = e.getMessage();
            log.error(error);
            ResponseDTO<SurveyIdDTO> response = ResponseDTO.<SurveyIdDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}

