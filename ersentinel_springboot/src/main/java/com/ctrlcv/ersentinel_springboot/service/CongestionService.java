package com.ctrlcv.ersentinel_springboot.service;

import com.ctrlcv.ersentinel_springboot.data.repository.HospitalRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;


@Service
public class CongestionService {
    @Autowired
    private HospitalRepository repository;


//    private void validate(final Dawon entity){
//        if(entity == null){
//            throw new RuntimeException("Dawon entity cannot be null");
//        }
//    }

    public List<Object[]> retrieveHospitalAndEmgRoomByFirstAddress(final String firstAddress){ // fa으로 필터링 해서 반환
        return repository.findHospitalAndEmgRoomByFirstAddress(firstAddress);
    }

    public List<Object[]> retrieveHospitalAndEmgRoomBySecondAddress(final String secondAddress){ // sa으로 필터링 해서 반환
        return repository.findHospitalAndEmgRoomBySecondAddress(secondAddress);
    }

}
