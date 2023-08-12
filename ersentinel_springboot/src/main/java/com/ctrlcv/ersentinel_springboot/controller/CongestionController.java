package com.ctrlcv.ersentinel_springboot.controller;

import com.ctrlcv.ersentinel_springboot.data.dto.CongestionDTO;
import com.ctrlcv.ersentinel_springboot.data.dto.ResponseDTO;
import com.ctrlcv.ersentinel_springboot.data.entity.CongestionEntity;
import com.ctrlcv.ersentinel_springboot.service.CongestionService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/congestion")
public class CongestionController {
    @Autowired
    private CongestionService service;

    private void validate(final CongestionEntity entity){
        if(entity == null){
            throw new RuntimeException("Dawon entity cannot be null");
        }

    }

//    class adultCompare implements Comparator<CongestionEntity>{
//        @Override
//        public int compare(CongestionEntity o1, CongestionEntity o2) {
//            return o1.adultAvailableBeds / o1.adultStandardBeds - o2.adultAvailableBeds / o2.adultStandardBeds;
//        }
//    }

    @GetMapping("/congestionlist")
    private ResponseEntity<?> congestionList(@RequestParam(value = "fA") String firstAddress, @RequestParam(value = "isadult") boolean isadult){
        try{
            List<CongestionEntity> entities = service.retrieve(firstAddress);
            ArrayList<CongestionEntity> sortlist = new ArrayList<>();
            if(isadult){
                for(CongestionEntitu entity : entities){
                    if(entity.adultAvailableBeds == null || entity.adultStandardBeds == null){
                        continue;
                    }
                    sortlist.add(entity);
                }

            }else{
                for(CongestionEntitu entity : entities){
                    if(entity.pediatricAvailableBeds == null || entity.pediatricStandardBeds == null){
                        continue;
                    }
                    sortlist.add(entity);
                }

            }
            if(isadult){
                Collections.sort(sortlist, new Comparator<CongestionEntity>() {
                    @Override
                    public int compare(CongestionEntity o1, CongestionEntity o2) {
                        return o1.adultAvailableBeds / o1.adultStandardBeds - o2.adultAvailableBeds / o2.adultStandardBeds;
                    }
                });
            }else{
                Collections.sort(sortlist, new Comparator<CongestionEntity>() {
                    @Override
                    public int compare(CongestionEntity o1, CongestionEntity o2) {
                        return o1.pediatricAvailableBeds / o1.pediatricStandardBeds - o2.pediatricAvailableBeds / o2.pediatricStandardBeds;
                    }
                });
            }
            List<CongestionDTO> dtos = entities.stream().map(CongestionDTO::new).collect(Collectors.toList());
            ResponseDTO<CongestionDTO> response = ResponseDTO.<CongestionDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<CongestionDTO> response = ResponseDTO.<CongestionDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }




}
