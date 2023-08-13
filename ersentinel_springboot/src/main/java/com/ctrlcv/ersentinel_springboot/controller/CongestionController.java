package com.ctrlcv.ersentinel_springboot.controller;

import com.ctrlcv.ersentinel_springboot.data.dto.CongestionDTO;
import com.ctrlcv.ersentinel_springboot.data.dto.ResponseDTO;
import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoom;
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
import java.util.Comparator;

@RestController
@RequestMapping("/congestion")
public class CongestionController {
    @Autowired
    private CongestionService service;

    private static final List<String> firstAddressQueryClassifingList = new ArrayList<>();
    //울산광역시 부산광역시 인천광역시 대구광역시 광주광역시 서울특별시 대전광역시 세종특별자치시
    static{
        firstAddressQueryClassifingList.add("울산광역시");
        firstAddressQueryClassifingList.add("부산광역시");
        firstAddressQueryClassifingList.add("인천광역시");
        firstAddressQueryClassifingList.add("대구광역시");
        firstAddressQueryClassifingList.add("광주광역시");
        firstAddressQueryClassifingList.add("서울특별시");
        firstAddressQueryClassifingList.add("대전광역시");
        firstAddressQueryClassifingList.add("세종특별자치시");
    }

//    private void validate(final CongestionEntity entity){
//        if(entity == null){
//            throw new RuntimeException("Dawon entity cannot be null");
//        }
//
//    }

//    class adultCompare implements Comparator<CongestionEntity>{
//        @Override
//        public int compare(CongestionEntity o1, CongestionEntity o2) {
//            return o1.adultAvailableBeds / o1.adultStandardBeds - o2.adultAvailableBeds / o2.adultStandardBeds;
//        }
//    }

    @GetMapping("/congestionlist")
    private ResponseEntity<?> congestionList(@RequestParam(value = "firstAddress") String firstAddress, @RequestParam(value = "secondAddress") String secondAddress, @RequestParam(value = "isadult") boolean isadult){
        try{
            List<Object[]> objs;
            if (firstAddressQueryClassifingList.contains(firstAddress)){
                objs = service.retrieveHospitalAndEmgRoomByFirstAddress(firstAddress);
            }else{
                objs = service.retrieveHospitalAndEmgRoomBySecondAddress(secondAddress);
            }


            ArrayList<Object[]> sortlist = new ArrayList<>();
            if(isadult){
                for(Object[] obj : objs){
                    EmergencyRoom emergencyRoom = (EmergencyRoom) obj[1];
                    if(emergencyRoom.getAdultAvailableBeds() == -1 || emergencyRoom.getAdultStandardBeds() == -1){
                        // TODO: 이거 재욱이형한테 null어떤식으로 되는지 물어보고 바꿔야함 꼭!!!!!!!
                        continue;
                    }
                    sortlist.add(obj);
                }

            }else{
                for(Object[] obj : objs){
                    EmergencyRoom emergencyRoom = (EmergencyRoom) obj[1];
                    if(emergencyRoom.getPediatricAvailableBeds() == -1 || emergencyRoom.getPediatricStandardBeds() == -1){
                        continue;
                    }
                    sortlist.add(obj);
                }

            }
            if(isadult){
                Collections.sort(sortlist, new Comparator<Object[]>() {
                    @Override
                    public int compare(Object[] o1, Object[] o2) {
                        EmergencyRoom emergencyRoom1 = (EmergencyRoom) o1[1];
                        EmergencyRoom emergencyRoom2 = (EmergencyRoom) o2[1];
                        double ratio1 = (double) emergencyRoom1.getAdultAvailableBeds() / emergencyRoom1.getAdultStandardBeds();
                        double ratio2 = (double) emergencyRoom2.getAdultAvailableBeds() / emergencyRoom2.getAdultStandardBeds();
                        return Double.compare(ratio1, ratio2);
                        //TODO: 나눗셈할때 double형으로 잘 변환되는지 확인
                    }
                });
            }else{
                Collections.sort(sortlist, new Comparator<Object[]>() {
                    @Override
                    public int compare(Object[] o1, Object[] o2) {
                        EmergencyRoom emergencyRoom1 = (EmergencyRoom) o1[1];
                        EmergencyRoom emergencyRoom2 = (EmergencyRoom) o2[1];
                        double ratio1 = (double) emergencyRoom1.getPediatricAvailableBeds() / emergencyRoom1.getPediatricStandardBeds();
                        double ratio2 = (double) emergencyRoom2.getPediatricAvailableBeds() / emergencyRoom2.getPediatricStandardBeds();
                        return Double.compare(ratio1, ratio2);
                        //TODO: 나눗셈할때 double형으로 잘 변환되는지 확인
                    }
                });

            }
            List<CongestionDTO> dtos = new ArrayList<>();
            for(Object[] obj : sortlist){
                Hospital hospital = (Hospital) obj[0];
                EmergencyRoom emergencyRoom = (EmergencyRoom) obj[1];
                dtos.add(new CongestionDTO(hospital,emergencyRoom));
            }
            ResponseDTO<CongestionDTO> response = ResponseDTO.<CongestionDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<CongestionDTO> response = ResponseDTO.<CongestionDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }




}

/*
* 경기도 강원특별자치도 경상남도 충청북도 경상북도 전라남도 전라북도 충청남도 제주특별자치도
* 울산광역시 부산광역시 인천광역시 대구광역시 광주광역시 서울특별시 대전광역시 세종특별자치시
* ['울산광역시', '부산광역시', '인천광역시', '경기도', '대구광역시', '광주광역시', '서울특별시', '강원특별자치도', '경상남도',
* '충청북도', '경상북도', '전라남도', '대전광역시', '전라북도', '충청남도', '세종특별자치시', '제주특별자치도']
* */