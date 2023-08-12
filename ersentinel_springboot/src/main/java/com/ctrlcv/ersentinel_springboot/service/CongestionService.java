package com.ctrlcv.ersentinel_springboot.service;

import com.ctrlcv.ersentinel_springboot.data.repository.CongestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;


@Service
public class CongestionService {
    @Autowired
    private CongestionRepository repository;


//    private void validate(final Dawon entity){
//        if(entity == null){
//            throw new RuntimeException("Dawon entity cannot be null");
//        }
//    }

    public List<CongestionEntity> retrieve(final String firstAddress){ // 시도군으로 필터링 해서 반환
        return repository.findByfirstAddress();
    }

}
