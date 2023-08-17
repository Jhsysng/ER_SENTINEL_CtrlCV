package com.ctrlcv.ersentinel_springboot.data.repository;

import com.ctrlcv.ersentinel_springboot.data.entity.Survey;
import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Integer> {
    //Optional<Survey> findByEmgMessage(String emgMessage);
    Optional<Survey[]> findByHospitalDutyId(String dutyId);
}
