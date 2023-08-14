package com.ctrlcv.ersentinel_springboot.data.repository;

import com.ctrlcv.ersentinel_springboot.data.entity.EmergencyRoom;
import com.ctrlcv.ersentinel_springboot.data.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository extends JpaRepository<Hospital, String> {
// 예시
//            hospitalRepository.findHospitalAndEmgRoomByFirstAddress("서울특별시")
//                    .forEach(objects -> {
//        Hospital hospital = (Hospital) objects[0];
//        EmergencyRoom emergencyRoom = (EmergencyRoom) objects[1];
//        log.info("hospital: " + hospital.getName() + " " + hospital.getDutyId() + " " + hospital.getPhoneNumber() + " " + hospital.getAddress());
//        log.info("emergencyRoom: " + emergencyRoom.getHospital().getName() + " " +emergencyRoom.getHospital().getDutyId() + " " + emergencyRoom.getAdultAvailableBeds());
//    });
    @Query("SELECT h, e FROM Hospital h JOIN EmergencyRoom e ON h.dutyId = e.hospital.dutyId WHERE h.firstAddress = :firstAddress")
    List<Object[]> findHospitalAndEmgRoomByFirstAddress(@Param("firstAddress") String firstAddress);

    @Query("SELECT h, e FROM Hospital h JOIN EmergencyRoom e ON h.dutyId = e.hospital.dutyId WHERE h.secondAddress = :secondAddress")
    List<Object[]> findHospitalAndEmgRoomBySecondAddress(@Param("secondAddress") String secondAddress);
    // TODO : 재욱이형한테 secondAddress 오타바꾼거 알려주기
}
