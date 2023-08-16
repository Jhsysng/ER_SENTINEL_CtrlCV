package com.ctrlcv.ersentinel_springboot.controller;

import com.ctrlcv.ersentinel_springboot.data.dto.*;
import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyMessage;
import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoomSevereCapacityInfo;
import com.ctrlcv.ersentinel_springboot.data.entity.HospitalEquipment;
import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoom;
import com.ctrlcv.ersentinel_springboot.service.HospitalDetailService;
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
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/hospitaldetail")
public class HospitalDetailController {
    @Autowired
    private HospitalDetailService service;


    @GetMapping("/congestioninfo")
    private ResponseEntity<?> congestion(@RequestParam(value = "dutyId") String dutyId){
        try{
            log.info(dutyId);
            Optional<EmergencyRoom> objs;
            objs = service.retrieveEmgRoomByDutyId(dutyId);
            if (objs.isEmpty()) {
                log.info("there is no hospital matching dutyId");
                ResponseDTO<CongestionInfoDTO> response = ResponseDTO.<CongestionInfoDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            List<CongestionInfoDTO> dtos = new ArrayList<>();

            EmergencyRoom emergencyRoom = objs.get();
            dtos.add(new CongestionInfoDTO(emergencyRoom));


            ResponseDTO<CongestionInfoDTO> response = ResponseDTO.<CongestionInfoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String error = e.getMessage();
            log.info(error);
            ResponseDTO<CongestionInfoDTO> response = ResponseDTO.<CongestionInfoDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/hospitalequipment")
    private ResponseEntity<?> equipment(@RequestParam(value = "dutyId") String dutyId){
        try{
            log.info(dutyId);
            Optional<HospitalEquipment> objs;
            objs = service.retrieveHospitalEquipmentByDutyId(dutyId);
            if (objs.isEmpty()) {
                log.info("there is no hospital matching dutyId");
                ResponseDTO<HospitalEquipmentDTO> response = ResponseDTO.<HospitalEquipmentDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            List<HospitalEquipmentDTO> dtos = new ArrayList<>();

            HospitalEquipment hospitalEquipment = objs.get();
            dtos.add(new HospitalEquipmentDTO(hospitalEquipment));


            ResponseDTO<HospitalEquipmentDTO> response = ResponseDTO.<HospitalEquipmentDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String error = e.getMessage();
            log.info(error);
            ResponseDTO<HospitalEquipmentDTO> response = ResponseDTO.<HospitalEquipmentDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/emergencyroomseverecapacityinfo")
    private ResponseEntity<?> ERSCI(@RequestParam(value = "dutyId") String dutyId){
        try{
            log.info(dutyId);
            Optional<EmergencyRoomSevereCapacityInfo> objs;
            objs = service.retrieveEmergencyRoomSevereCapacityInfoByDutyId(dutyId);
            if (objs.isEmpty()) {
                log.info("there is no hospital matching dutyId");
                ResponseDTO<EmergencyRoomSevereCapacityInfoDTO> response = ResponseDTO.<EmergencyRoomSevereCapacityInfoDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            List<EmergencyRoomSevereCapacityInfoDTO> dtos = new ArrayList<>();

            EmergencyRoomSevereCapacityInfo emergencyRoomSevereCapacityInfo = objs.get();
            dtos.add(new EmergencyRoomSevereCapacityInfoDTO(emergencyRoomSevereCapacityInfo));


            ResponseDTO<EmergencyRoomSevereCapacityInfoDTO> response = ResponseDTO.<EmergencyRoomSevereCapacityInfoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String error = e.getMessage();
            log.info(error);
            ResponseDTO<EmergencyRoomSevereCapacityInfoDTO> response = ResponseDTO.<EmergencyRoomSevereCapacityInfoDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/roadmap")
    private ResponseEntity<?> roadmap(@RequestParam(value = "dutyId") String dutyId){
        try{
            log.info(dutyId);
            Optional<EmergencyRoom> objs;
            objs = service.retrieveEmgRoomByDutyId(dutyId);
            if (objs.isEmpty()) {
                log.info("there is no hospital matching dutyId");
                ResponseDTO<RoadMapDTO> response = ResponseDTO.<RoadMapDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }

            List<RoadMapDTO> dtos = new ArrayList<>();

            EmergencyRoom emergencyRoom = objs.get();
            dtos.add(new RoadMapDTO(emergencyRoom));


            ResponseDTO<RoadMapDTO> response = ResponseDTO.<RoadMapDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String error = e.getMessage();
            log.info(error);
            ResponseDTO<RoadMapDTO> response = ResponseDTO.<RoadMapDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/emergencymessage")
    private ResponseEntity<?> emergencymessage(@RequestParam(value = "dutyId") String dutyId){
        try{
            log.info(dutyId);
            Optional<EmergencyMessage[]> objs;
            objs = service.retrieveEmergencyMessageByDutyId(dutyId);
            if (objs.isEmpty()) {
                log.info("there is no messages matching dutyId");
                ResponseDTO<RoadMapDTO> response = ResponseDTO.<RoadMapDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }
            EmergencyMessage[] messages = objs.get();
            if(messages.length==0){
                log.info("there is no messages matching dutyId");
                ResponseDTO<RoadMapDTO> response = ResponseDTO.<RoadMapDTO>builder().error(true).build();
                return ResponseEntity.badRequest().body(response);
            }


            List<EmergencyMessageDTO> dtos = new ArrayList<>();

            for(EmergencyMessage message : messages) {
                dtos.add(new EmergencyMessageDTO(message));
            }



            ResponseDTO<EmergencyMessageDTO> response = ResponseDTO.<EmergencyMessageDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String error = e.getMessage();
            log.info(error);
            ResponseDTO<EmergencyMessageDTO> response = ResponseDTO.<EmergencyMessageDTO>builder().error(true).build();
            return ResponseEntity.badRequest().body(response);
        }
    }







}


