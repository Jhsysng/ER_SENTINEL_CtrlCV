package com.ctrlcv.ersentinel_springboot.service;

import com.ctrlcv.ersentinel_springboot.data.entity.Survey;
import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import com.ctrlcv.ersentinel_springboot.data.entity.User;
import com.ctrlcv.ersentinel_springboot.data.repository.HospitalRepository;
import com.ctrlcv.ersentinel_springboot.data.repository.SurveyRepository;
import com.ctrlcv.ersentinel_springboot.data.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SurveyService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Survey[]> create(final Survey entity) {

        //log.info(Integer.toString(entity.getStar()));
        surveyRepository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());
        //return repository.findByUserId(entity.getUserId());
        return surveyRepository.findByHospitalDutyId(entity.getHospital().getDutyId());
    }

//    private void validate(final TodoEntity entity) {
//        if(entity == null) {
//            log.warn("Entity cannot be null.");
//            throw new RuntimeException("Entity cannot be null.");
//        }
//
//        if(entity.getUserId() == null) {
//            log.warn("Unknown user.");
//            throw new RuntimeException("Unknown user.");
//        }
//    }

//    public List<Survey> retrieve(final String userId) {
//        return repository.findByUserId(userId);
//    }

    public Optional<Survey[]> update(final Survey entity) {
        final Optional<Survey> original = surveyRepository.findById(entity.getId());

        original.ifPresent(survey -> {
            survey.setStar(entity.getStar());
            survey.setShortMessage(entity.getShortMessage());
            surveyRepository.save(survey);
        });

        //TODO: user?
        return surveyRepository.findByHospitalDutyId(entity.getHospital().getDutyId());
    }


    public Optional<Survey[]> delete(final Survey entity) {
        try {
            surveyRepository.delete(entity);
        } catch(Exception e) {
            log.error("error deleting entity ", entity.getId(), e);
            throw new RuntimeException("error deleting entity " + entity.getId());
        }
        return surveyRepository.findByHospitalDutyId(entity.getHospital().getDutyId());
    }

    public Optional<Hospital> retrieveHospitalByDutyId(final String dutyId){
        List<Hospital> hospitals = hospitalRepository.findByDutyId(dutyId);
        if (hospitals.isEmpty()){
            log.info("no hospital matching dutyId");
            throw new RuntimeException("error searching hospital entity");
        }
        try{
            return Optional.of(hospitals.get(0));
        }catch(Exception e) {
            throw new RuntimeException("error searching hospital entity");
        }

    }

    public Optional<Survey[]> retrieveSurveyByDutyId(final String dutyId){
        return surveyRepository.findByHospitalDutyId(dutyId);

    }

    public Optional<User> retrieveUserByUsername(final String username){
        return userRepository.findByUsername(username);
    }

    public Optional<Survey> retrieveSurveyById(final int id){
        return surveyRepository.findById(id);
    }




}
