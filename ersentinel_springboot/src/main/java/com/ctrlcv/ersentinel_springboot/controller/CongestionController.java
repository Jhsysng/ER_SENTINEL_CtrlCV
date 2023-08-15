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
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/congestion")
public class CongestionController {
    @Autowired
    private CongestionService service;

    private static HashMap<String, String> firstAddressQueryClassifindingHashMap = new HashMap<String, String>();
    //울산광역시 부산광역시 인천광역시 대구광역시 광주광역시 서울특별시 대전광역시 세종특별자치시
    private static HashMap<String, String> secondAddressQueryClassifindingHashMap = new HashMap<String, String>();
    //경기도 강원특별자치도 경상남도 충청북도 경상북도 전라남도 전라북도 충청남도 제주특별자치도

    static{ // TODO: 은동이한테 정보 받아서 채워넣기
        firstAddressQueryClassifindingHashMap.put("울산","울산광역시");
        firstAddressQueryClassifindingHashMap.put("부산","부산광역시");
        firstAddressQueryClassifindingHashMap.put("인천","인천광역시");
        firstAddressQueryClassifindingHashMap.put("대구","대구광역시");
        firstAddressQueryClassifindingHashMap.put("광주","광주광역시");
        firstAddressQueryClassifindingHashMap.put("서울","서울특별시");
        firstAddressQueryClassifindingHashMap.put("대전","대전광역시");
        firstAddressQueryClassifindingHashMap.put("세종특별자치시","세종특별자치시");

        secondAddressQueryClassifindingHashMap.put("경기","경기도");
        secondAddressQueryClassifindingHashMap.put("강원특별자치도","강원특별자치도");
        secondAddressQueryClassifindingHashMap.put("경남","경상남도");
        secondAddressQueryClassifindingHashMap.put("충북","충청북도");
        secondAddressQueryClassifindingHashMap.put("경북","경상북도");
        secondAddressQueryClassifindingHashMap.put("전남","전라남도");
        secondAddressQueryClassifindingHashMap.put("전북","전라북도");
        secondAddressQueryClassifindingHashMap.put("충남","충청남도");
        secondAddressQueryClassifindingHashMap.put("제주특별자치도","제주특별자치도");


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
        // TODO: 은동이한테 매핑 데이터 받아서 매핑해주는 코드 구현 + 클라이언트로 부터 제대로된 요청이 왔는지 확인
        try{
            log.info(firstAddress);
            log.info(String.valueOf(isadult));
            List<Object[]> objs;
            if (firstAddressQueryClassifindingHashMap.containsKey(firstAddress)){
                objs = service.retrieveHospitalAndEmgRoomByFirstAddress(firstAddressQueryClassifindingHashMap.get(firstAddress));
            }else if (secondAddressQueryClassifindingHashMap.containsKey(firstAddress)){
                objs = service.retrieveHospitalAndEmgRoomBySecondAddress(secondAddress); // TODO: 이거 프론트에서 잘못된 형식으로 넘겨주면 어떡함?
            }else{
                // 데이터가 없다고 예외처리 한것이 아님. 애초에 대한민국 팔도에 없는 지역이 입력으로 들어옴 (혹은 팔도인데 사전에 정의되지 않은 방법으로 들어옴)
                log.info("wrong address");
                String error = "wrong address";
                ResponseDTO<CongestionDTO> response = ResponseDTO.<CongestionDTO>builder().error(error).build();
                return ResponseEntity.badRequest().body(response);
            }
            if (objs == null){
                log.info("result of query is null");
                String error = "result of query is null";
                ResponseDTO<CongestionDTO> response = ResponseDTO.<CongestionDTO>builder().error(error).build();
                return ResponseEntity.badRequest().body(response);
            }else if (objs.isEmpty()){ // 여기는 도단위로 다시 매핑해준다.
                log.info("there is no hospital in location");
                objs = service.retrieveHospitalAndEmgRoomByFirstAddress(firstAddressQueryClassifindingHashMap.get(firstAddress));
            }
            // 여기는 데이터가 없을때임. 가용이고 자시고 걍 병원이 없음
            // [] 가 반환됨 List.isEmpty
            ArrayList<Object[]> sortlist = new ArrayList<>();
            if(isadult){
                for(Object[] obj : objs){
                    EmergencyRoom emergencyRoom = (EmergencyRoom) obj[1];
                    if(emergencyRoom.getAdultAvailableBeds() == Integer.MIN_VALUE || emergencyRoom.getAdultStandardBeds() < 1){
                        // 해당되는 연령의 데이터가 없으면 리스팅하지 않는다.
                        // 전체병상이 0일때 0으로 나누는 연산이 있기에 여기서 겸사겸사 처리한다.
                        // INT.min? Integer.MIN_VALUE?
                        continue;
                    }
                    sortlist.add(obj);
                }

            }else{
                for(Object[] obj : objs){
                    EmergencyRoom emergencyRoom = (EmergencyRoom) obj[1];
                    if(emergencyRoom.getPediatricAvailableBeds() == Integer.MIN_VALUE || emergencyRoom.getPediatricStandardBeds() < 1){
                        continue;
                    }
                    sortlist.add(obj);
                }

            }
            if (sortlist.isEmpty()){
                log.info("nothing to show");
                String error = "nothing to show";
                ResponseDTO<CongestionDTO> response = ResponseDTO.<CongestionDTO>builder().error(error).build();
                return ResponseEntity.badRequest().body(response);
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
*
* 보안약점 노트북
* 사용자가 해외에 있을때 입력이 어떻게 올것인가? - sql injection?
* --> 입력을 화이트 리스트, 블랙 리스트로 관리.
*
*
*
* 걍 시큐어코딩 공부용
* 동적, 정적 쿼리
* 비밀번호 -> 일방향 해쉬 암호화 sha256
* 하드코드 암호화 키
* 시간 및 상태 (병렬 시스템) TOCTOU, run()
* 오류처리 - e.printstacktrace 조심.
* try catch finally
* */