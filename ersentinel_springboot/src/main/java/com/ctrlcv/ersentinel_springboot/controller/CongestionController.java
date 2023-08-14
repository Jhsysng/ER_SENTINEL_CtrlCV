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
        firstAddressQueryClassifindingHashMap.put("1","울산광역시");
        firstAddressQueryClassifindingHashMap.put("2","부산광역시");
        firstAddressQueryClassifindingHashMap.put("3","인천광역시");
        firstAddressQueryClassifindingHashMap.put("4","대구광역시");
        firstAddressQueryClassifindingHashMap.put("5","광주광역시");
        firstAddressQueryClassifindingHashMap.put("6","서울특별시");
        firstAddressQueryClassifindingHashMap.put("7","대전광역시");
        firstAddressQueryClassifindingHashMap.put("8","세종특별자치시");

        secondAddressQueryClassifindingHashMap.put("1","경기도");
        secondAddressQueryClassifindingHashMap.put("2","강원특별자치도");
        secondAddressQueryClassifindingHashMap.put("3","경상남도");
        secondAddressQueryClassifindingHashMap.put("4","충청북도");
        secondAddressQueryClassifindingHashMap.put("5","경상북도");
        secondAddressQueryClassifindingHashMap.put("6","전라남도");
        secondAddressQueryClassifindingHashMap.put("7","전라북도");
        secondAddressQueryClassifindingHashMap.put("8","충청남도");
        secondAddressQueryClassifindingHashMap.put("9","제주특별자치도");


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
                // TODO: 잘못된 지역이 입력되면 어떻게 할것인가? (회의 안건)
            }
            // 여기는 데이터가 없을때임. 가용이고 자시고 걍 병원이 없음
            // TODO: objs 가 유효한지 검사해햐 함. 재욱이형 한테 쿼리 날려서 온게 아무것도 없을때 어케되는지 물어보기

            ArrayList<Object[]> sortlist = new ArrayList<>();
            if(isadult){
                for(Object[] obj : objs){
                    EmergencyRoom emergencyRoom = (EmergencyRoom) obj[1];
                    if(emergencyRoom.getAdultAvailableBeds() == -1 || emergencyRoom.getAdultStandardBeds() < 1){
                        // 해당되는 연령의 데이터가 없으면 리스팅하지 않는다.
                        // 전체병상이 0일때 0으로 나누는 연산이 있기에 여기서 겸사겸사 처리한다.
                        // TODO: 이거 재욱이형한테 null어떤식으로 되는지 물어보고 바꿔야함 꼭!!!!!!!
                        continue;
                    }
                    sortlist.add(obj);
                }

            }else{
                for(Object[] obj : objs){
                    EmergencyRoom emergencyRoom = (EmergencyRoom) obj[1];
                    if(emergencyRoom.getPediatricAvailableBeds() == -1 || emergencyRoom.getPediatricStandardBeds() < 1){
                        // 해당되는 연령의 데이터가 없으면 리스팅하지 않는다.
                        // 전체병상이 0일때 0으로 나누는 연산이 있기에 여기서 겸사겸사 처리한다.
                        // TODO: 이거 재욱이형한테 null어떤식으로 되는지 물어보고 바꿔야함 꼭!!!!!!!
                        continue;
                    }
                    sortlist.add(obj);
                }

            }
            // TODO: 병원이 없는게 아니라 데이터를 제공안해줘서 sortlist가 비어버림 이거 에러처리? (회의안건)
            if (sortlist.isEmpty()){
                log.info("nothing to show");
                String error = "nothing to show";
                ResponseDTO<CongestionDTO> response = ResponseDTO.<CongestionDTO>builder().error(error).build();
                return ResponseEntity.badRequest().body(response);
            }

            if(isadult){
                Collections.sort(sortlist, new Comparator<Object[]>() {
                    @Override
                    public int compare(Object[] o1, Object[] o2) {
                        EmergencyRoom emergencyRoom1 = (EmergencyRoom) o1[1];
                        EmergencyRoom emergencyRoom2 = (EmergencyRoom) o2[1];
//                        double ratio1 = (double) emergencyRoom1.getAdultAvailableBeds() / emergencyRoom1.getAdultStandardBeds();
//                        double ratio2 = (double) emergencyRoom2.getAdultAvailableBeds() / emergencyRoom2.getAdultStandardBeds();
//                        return Double.compare(ratio1, ratio2); --> 같은의미
                        return Double.compare((double) emergencyRoom1.getAdultAvailableBeds() / emergencyRoom1.getAdultStandardBeds(), (double) emergencyRoom2.getAdultAvailableBeds() / emergencyRoom2.getAdultStandardBeds());
                        //TODO: 나눗셈할때 double형으로 잘 변환되는지 확인
                    }
                });
            }else{
                Collections.sort(sortlist, new Comparator<Object[]>() {
                    @Override
                    public int compare(Object[] o1, Object[] o2) {
                        EmergencyRoom emergencyRoom1 = (EmergencyRoom) o1[1];
                        EmergencyRoom emergencyRoom2 = (EmergencyRoom) o2[1];
//                        double ratio1 = (double) emergencyRoom1.getPediatricAvailableBeds() / emergencyRoom1.getPediatricStandardBeds();
//                        double ratio2 = (double) emergencyRoom2.getPediatricAvailableBeds() / emergencyRoom2.getPediatricStandardBeds();
//                        return Double.compare(ratio1, ratio2); --> 같은의미
                        return Double.compare((double) emergencyRoom1.getPediatricAvailableBeds() / emergencyRoom1.getPediatricStandardBeds(), (double) emergencyRoom2.getPediatricAvailableBeds() / emergencyRoom2.getPediatricStandardBeds());
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

//    TODO : 주석 싹 지우고 위 코드 SORTING 지우고 다시 코딩
//    @GetMapping("/congestionmap") // TODO : 지도는 해당되는 값이 없더라도 보여줘야되는거 아님? (회의안건)
//    private ResponseEntity<?> congestionMap(@RequestParam(value = "firstAddress") String firstAddress, @RequestParam(value = "secondAddress") String secondAddress, @RequestParam(value = "isadult") boolean isadult){
//        try{
//            List<Object[]> objs;
//            if (firstAddressQueryClassifingList.contains(firstAddress)){
//                objs = service.retrieveHospitalAndEmgRoomByFirstAddress(firstAddress);
//            }else{
//                objs = service.retrieveHospitalAndEmgRoomBySecondAddress(secondAddress);
//            }
//
//
//            ArrayList<Object[]> availablelist = new ArrayList<>();
//            if(isadult){
//                for(Object[] obj : objs){
//                    EmergencyRoom emergencyRoom = (EmergencyRoom) obj[1];
//                    if(emergencyRoom.getAdultAvailableBeds() == -1 || emergencyRoom.getAdultStandardBeds() < 1){
//                        // TODO: 이거 재욱이형한테 null어떤식으로 되는지 물어보고 바꿔야함 꼭!!!!!!!
//                        continue;
//                    }
//                    availablelist.add(obj);
//                }
//
//            }else{
//                for(Object[] obj : objs){
//                    EmergencyRoom emergencyRoom = (EmergencyRoom) obj[1];
//                    if(emergencyRoom.getPediatricAvailableBeds() == -1 || emergencyRoom.getPediatricStandardBeds() < 1){
//                        continue;
//                    }
//                    availablelist.add(obj);
//                }
//
//            }
//
//            List<CongestionDTO> dtos = new ArrayList<>();
//            for(Object[] obj : availablelist){
//                Hospital hospital = (Hospital) obj[0];
//                EmergencyRoom emergencyRoom = (EmergencyRoom) obj[1];
//                dtos.add(new CongestionDTO(hospital,emergencyRoom));
//            }
//            ResponseDTO<CongestionDTO> response = ResponseDTO.<CongestionDTO>builder().data(dtos).build();
//            return ResponseEntity.ok().body(response);
//        } catch (Exception e){
//            String error = e.getMessage();
//            ResponseDTO<CongestionDTO> response = ResponseDTO.<CongestionDTO>builder().error(error).build();
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

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