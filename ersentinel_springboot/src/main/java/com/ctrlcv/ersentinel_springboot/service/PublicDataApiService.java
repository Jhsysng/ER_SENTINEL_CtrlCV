package com.ctrlcv.ersentinel_springboot.service;

import com.ctrlcv.ersentinel_springboot.component.ApiURLs;
import com.ctrlcv.ersentinel_springboot.data.dto.xml.*;
import com.ctrlcv.ersentinel_springboot.data.entity.*;
import com.ctrlcv.ersentinel_springboot.data.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.UnmarshalException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.StringReader;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@EnableScheduling
@Slf4j
public class PublicDataApiService {
    private final EmergencyMessageRepository emergencyMessageRepository;
    private final EmergencyRoomRepository emergencyRoomRepository;
    private final EmergencyRoomSevereCapacityInfoRepository emergencyRoomSevereCapacityInfoRepository;
    private final HospitalEquipmentRepository hospitalEquipmentRepository;
    private final HospitalRepository hospitalRepository;

    @Value("${api.public-data.key}")
    private String serviceKey;

    @Value("${api.public-data.numOfRows}")
    private int numOfRows;

    public PublicDataApiService(EmergencyMessageRepository emergencyMessageRepository, EmergencyRoomRepository emergencyRoomRepository, EmergencyRoomSevereCapacityInfoRepository emergencyRoomSevereCapacityInfoRepository, HospitalEquipmentRepository hospitalEquipmentRepository, HospitalRepository hospitalRepository) {
        this.emergencyMessageRepository = emergencyMessageRepository;
        this.emergencyRoomRepository = emergencyRoomRepository;
        this.emergencyRoomSevereCapacityInfoRepository = emergencyRoomSevereCapacityInfoRepository;
        this.hospitalEquipmentRepository = hospitalEquipmentRepository;
        this.hospitalRepository = hospitalRepository;
    }

    @Scheduled(cron = "0 0 0/1 * * *")
    @Transactional
    public void apiRequest() {
        log.info("공공 데이터 포털 API 요청 시작");
        getEmergencyDeptListInfo();
        getEmergencyRoomRealTimeAvlBedInfo();
        getSevereDiseaseAcceptancePossibleInfo();
        getEmergencyRoomAndSevereDiseaseMessageInfo();
        log.info("공공 데이터 포털 API 요청 종료");
    }

    /**
     * 응급의료기관 위치정보 조회
     */
    @Transactional
    public List<Hospital> getEmergencyDeptListInfoByLonLat(double lon, double lat) {
        log.info("응급의료기관 위치정보 조회 API 요청 시작 : getEmergecyDeptListInfoByLatLot");
        String url = ApiURLs.EmergencyDeptListByLonLatInfo.getDefaultUrlWithLonLat(1, numOfRows, serviceKey, lon, lat);
        Optional<String> xmlData = getXmlDataByApi(url);

        int totalCount = 0;

        List<Hospital> hospitalList = new ArrayList<Hospital>();

        EmergencyDeptListInfoByLatLonResponse response = null;

        if (xmlData.isPresent()) {
            response = parsingEmergencyDeptListInfoByLonLat(xmlData.get());
            if (response != null) {
                totalCount = response.getBody().getTotalCount();
                hospitalList.addAll(getHospitalListFromLonLatReq(response));
            } else {
                return hospitalList;
            }
        }

        int nowCount = numOfRows;
        for (int pageNum = 2; nowCount <= totalCount; pageNum++) {
            url = ApiURLs.EmergencyDeptListByLonLatInfo.getDefaultUrlWithLonLat(pageNum, numOfRows, serviceKey, lon, lat);
            xmlData = getXmlDataByApi(url);

            if (xmlData.isPresent()) {
                response = parsingEmergencyDeptListInfoByLonLat(xmlData.get());
                if (response != null) {
                    hospitalList.addAll(getHospitalListFromLonLatReq(response));
                }
            }
            nowCount += numOfRows;
        }
        return hospitalList;
    }

    @Transactional
    private EmergencyDeptListInfoByLatLonResponse parsingEmergencyDeptListInfoByLonLat(String xmlData) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(EmergencyDeptListInfoByLatLonResponse.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (EmergencyDeptListInfoByLatLonResponse) unmarshaller.unmarshal(new StringReader(xmlData));
        } catch (UnmarshalException unmarshalException) {
            unmarshalException.printStackTrace();
            log.error("parsingEmergencyDeptListInfoByLonLat : Response Xml Data 파싱 오류입니다.");
            return null;
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error("parsingEmergencyDeptListInfoByLonLat : Response Xml Data 이 Null 입니다.");
            return null;
        } catch (JAXBException e) {
            log.error("parsingEmergencyDeptListInfoByLonLat : JAXBContext 를 생성할 수 없습니다.");
            return null;
        }
    }

    @Transactional
    private List<Hospital> getHospitalListFromLonLatReq(EmergencyDeptListInfoByLatLonResponse response) {
        List<Hospital> hospitalList = new ArrayList<Hospital>();
        List<EmergencyDeptListInfoByLatLonResponse.Items.Item> itemList = response.getBody().getItems().getItem();
        if (itemList != null && !itemList.isEmpty()) {
            itemList.forEach(item -> {
                Hospital hospital = hospitalRepository.findById(item.getHpid())
                        .orElseThrow(() -> {
                            log.error("기관코드 : {} 에 해당하는 병원이 존재하지 않습니다.", item.getHpid());
                            throw new EntityNotFoundException("해당 병원이 존재하지 않습니다.");
                        });

                hospitalList.add(hospital);
            });
        }
        return hospitalList;
    }

    /**
     * 응급의료기관 목록정보 조회
     */
    @Transactional
    public void getEmergencyDeptListInfo() {
        log.info("응급의료기관 목록정보 조회 API 요청 시작 : getEmergencyDeptListInfo");
        String url = ApiURLs.EmergencyDeptListInfo.getDefaultUrl(1, numOfRows, serviceKey);
        Optional<String> xmlData = getXmlDataByApi(url);
        int totalCount = parsingEmergencyDeptListInfo(xmlData);
        int nowCount = numOfRows;

        for (int pageNum = 2; nowCount <= totalCount; pageNum++) {
            url = ApiURLs.EmergencyDeptListInfo.getDefaultUrl(pageNum, numOfRows, serviceKey);
            xmlData = getXmlDataByApi(url);
            parsingEmergencyDeptListInfo(xmlData);
            nowCount += numOfRows;
        }
    }

    @Transactional
    private int parsingEmergencyDeptListInfo(Optional<String> xmlData) {
        AtomicInteger totalCount = new AtomicInteger(0);
        xmlData.ifPresent(data -> {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(EmergencyDeptListInfoResponse.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                EmergencyDeptListInfoResponse response = (EmergencyDeptListInfoResponse) unmarshaller.unmarshal(new StringReader(data));

                totalCount.set(response.getBody().getTotalCount());

                List<EmergencyDeptListInfoResponse.Items.Item> itemList = response.getBody().getItems().getItem();
                if (itemList != null && !itemList.isEmpty()) {
                    itemList.forEach(item -> {
                        Optional<Hospital> isHospitalExist = hospitalRepository.findById(item.getHpid());
                        String[] address = item.getDutyAddr().split(" ");

                        if (isHospitalExist.isEmpty()) {
                            Hospital hospital = Hospital.builder()
                                    .dutyId(item.getHpid() != null ? item.getHpid() : "No Id")
                                    .name(item.getDutyName() != null ? item.getDutyName() : "No Name")
                                    .phoneNumber(item.getDutyTel3() != null ? item.getDutyTel3() : "No Phone Number")
                                    .longitude(item.getWgs84Lon() != null ? item.getWgs84Lon() : "No Longitude")
                                    .latitude(item.getWgs84Lat() != null ? item.getWgs84Lat() : "No Latitude")
                                    .address(item.getDutyAddr() != null ? item.getDutyAddr() : "No Address")
                                    .firstAddress(address[0] != null ? address[0] : "No First Address")
                                    .secondAddress(address[1] != null ? address[1] : "No Second Address")
                                    .build();
                            hospitalRepository.save(hospital);
                        } else {
                            Hospital hospital = isHospitalExist.get();
                            hospital.setDutyId(item.getHpid() != null ? item.getHpid() : "No Id");
                            hospital.setName(item.getDutyName() != null ? item.getDutyName() : "No Name");
                            hospital.setPhoneNumber(item.getDutyTel3() != null ? item.getDutyTel3() : "No Phone Number");
                            hospital.setLongitude(item.getWgs84Lon() != null ? item.getWgs84Lon() : "No Longitude");
                            hospital.setLatitude(item.getWgs84Lat() != null ? item.getWgs84Lat() : "No Latitude");
                            hospital.setAddress(item.getDutyAddr() != null ? item.getDutyAddr() : "No Address");
                            hospital.setFirstAddress(address[0] != null ? address[0] : "No First Address");
                            hospital.setSecondAddress(address[1] != null ? address[1] : "No Second Address");
                        }
                    });
                }
        } catch (UnmarshalException unmarshalException) {
            log.error("parsingEmergencyDeptListInfo : Response Xml Data 파싱 오류입니다.");
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error("parsingEmergencyDeptListInfo : Response Xml Data 이 Null 입니다.");
        } catch (JAXBException e) {
            log.error("parsingEmergencyDeptListInfo : JAXBContext 를 생성할 수 없습니다.");
        }
        });

        return totalCount.get();
    }

    /**
     * 중증질환자 수용가능정보 조회
     */
    @Transactional
    public void getSevereDiseaseAcceptancePossibleInfo() {
        log.info("중증질환자 수용가능정보 API 요청 시작 : getSevereDiseaseAcceptancePossibleInfo");
        String url = ApiURLs.SevereDiseaseAcceptancePossibleInfo.getDefaultUrl(1, numOfRows, serviceKey);
        Optional<String> xmlData = getXmlDataByApi(url);
        int totalCount = parsingSevereDiseaseAcceptancePossibleInfo(xmlData);
        int nowCount = numOfRows;

        for (int pageNum = 2; nowCount <= totalCount; pageNum++) {
            url = ApiURLs.SevereDiseaseAcceptancePossibleInfo.getDefaultUrl(pageNum, numOfRows, serviceKey);
            xmlData = getXmlDataByApi(url);
            parsingSevereDiseaseAcceptancePossibleInfo(xmlData);
            nowCount += numOfRows;
        }
    }

    @Transactional
    private int parsingSevereDiseaseAcceptancePossibleInfo(Optional<String> xmlData) {
        AtomicInteger totalCount = new AtomicInteger(0);
        xmlData.ifPresent(data -> {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(SevereDiseaseAcceptancePosblInfoResponse.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                SevereDiseaseAcceptancePosblInfoResponse response = (SevereDiseaseAcceptancePosblInfoResponse) unmarshaller.unmarshal(new StringReader(data));

                totalCount.set(response.getBody().getTotalCount());

                List<SevereDiseaseAcceptancePosblInfoResponse.Items.Item> itemList = response.getBody().getItems().getItem();

                if (itemList != null && !itemList.isEmpty()) {
                    itemList.forEach(item -> {
                        Hospital hospital = hospitalRepository.findById(item.getDutyName())
                                .orElseThrow(() -> {
                                    log.error("기관코드 : {} 에 해당하는 병원이 존재하지 않습니다.", item.getDutyName());
                                    throw new EntityNotFoundException("해당 병원이 존재하지 않습니다.");
                                });
                        Optional<EmergencyRoomSevereCapacityInfo> isEmergencyRoomSevereCapacityInfoExist = emergencyRoomSevereCapacityInfoRepository.findByHospitalDutyId(item.getDutyName());
                        if (isEmergencyRoomSevereCapacityInfoExist.isEmpty()) {
                            EmergencyRoomSevereCapacityInfo emergencyRoomSevereCapacityInfo = EmergencyRoomSevereCapacityInfo.builder()
                                    .hospital(hospital)
                                    .myocardialInfarction(item.getMKioskTy1() != null && item.getMKioskTy1().trim().equals("Y"))
                                    .cerebralInfarction(item.getMKioskTy2() != null && item.getMKioskTy2().trim().equals("Y"))
                                    .subarachnoidHemorrhage(item.getMKioskTy3() != null && item.getMKioskTy3().trim().equals("Y"))
                                    .otherBrainHemorrhage(item.getMKioskTy4() != null && item.getMKioskTy4().trim().equals("Y"))
                                    .thoracicAorta(item.getMKioskTy5() != null && item.getMKioskTy5().trim().equals("Y"))
                                    .abdominalAorta(item.getMKioskTy6() != null && item.getMKioskTy6().trim().equals("Y"))
                                    .gallbladderDisease(item.getMKioskTy7() != null && item.getMKioskTy7().trim().equals("Y"))
                                    .bileDuctDisease(item.getMKioskTy8() != null && item.getMKioskTy8().trim().equals("Y"))
                                    .nonTraumaticAbdominalEmergency(item.getMKioskTy9() != null && item.getMKioskTy9().trim().equals("Y"))
                                    .infantIntestinalObstruction(item.getMKioskTy10() != null && item.getMKioskTy10().trim().equals("Y"))
                                    .emergencyGastrointestinalEndoscopy(item.getMKioskTy11() != null && item.getMKioskTy11().trim().equals("Y"))
                                    .emergencyGastrointestinalEndoscopyForChildren(item.getMKioskTy12() != null && item.getMKioskTy12().trim().equals("Y"))
                                    .emergencyBronchoscopy(item.getMKioskTy13() != null && item.getMKioskTy13().trim().equals("Y"))
                                    .emergencyBronchoscopyForChildren(item.getMKioskTy14() != null && item.getMKioskTy14().trim().equals("Y"))
                                    .lowBirthWeightInfant(item.getMKioskTy15() != null && item.getMKioskTy15().trim().equals("Y"))
                                    .obstetricDelivery(item.getMKioskTy16() != null && item.getMKioskTy16().trim().equals("Y"))
                                    .obstetricSurgery(item.getMKioskTy17() != null && item.getMKioskTy17().trim().equals("Y"))
                                    .emergencyGynecologicalSurgery(item.getMKioskTy18() != null && item.getMKioskTy18().trim().equals("Y"))
                                    .severeBurns(item.getMKioskTy19() != null && item.getMKioskTy19().trim().equals("Y"))
                                    .limbReattachmentExtremities(item.getMKioskTy20() != null && item.getMKioskTy20().trim().equals("Y"))
                                    .limbReattachmentOther(item.getMKioskTy21() != null && item.getMKioskTy21().trim().equals("Y"))
                                    .emergencyDialysisHD(item.getMKioskTy22() != null && item.getMKioskTy22().trim().equals("Y"))
                                    .emergencyDialysisCRRT(item.getMKioskTy23() != null && item.getMKioskTy23().trim().equals("Y"))
                                    .psychiatry(item.getMKioskTy24() != null && item.getMKioskTy24().trim().equals("Y"))
                                    .ophthalmicSurgery(item.getMKioskTy25() != null && item.getMKioskTy25().trim().equals("Y"))
                                    .radiologyVascularIntervention(item.getMKioskTy26() != null && item.getMKioskTy26().trim().equals("Y"))
                                    .radiologyVascularInterventionForChildren(item.getMKioskTy27() != null && item.getMKioskTy27().trim().equals("Y"))
                                    .infantIntestinalAge(item.getMKioskTy10Msg() != null ? item.getMKioskTy10Msg() : "Not Provided")
                                    .gastrointestinalEndoscopyAge(item.getMKioskTy12Msg() != null ? item.getMKioskTy12Msg() : "Not Provided")
                                    .bronchoscopyAge(item.getMKioskTy14Msg() != null ? item.getMKioskTy14Msg() : "Not Provided")
                                    .lowBirthWeightAge(item.getMKioskTy15Msg() != null ? item.getMKioskTy15Msg() : "Not Provided")
                                    .radiologyAge(item.getMKioskTy27Msg() != null ? item.getMKioskTy27Msg() : "Not Provided")
                                    .build();

                            emergencyRoomSevereCapacityInfoRepository.save(emergencyRoomSevereCapacityInfo);
                        } else {
                            EmergencyRoomSevereCapacityInfo emergencyRoomSevereCapacityInfo = isEmergencyRoomSevereCapacityInfoExist.get();
                            emergencyRoomSevereCapacityInfo.setMyocardialInfarction(item.getMKioskTy1() != null && item.getMKioskTy1().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setCerebralInfarction(item.getMKioskTy2() != null && item.getMKioskTy2().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setSubarachnoidHemorrhage(item.getMKioskTy3() != null && item.getMKioskTy3().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setOtherBrainHemorrhage(item.getMKioskTy4() != null && item.getMKioskTy4().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setThoracicAorta(item.getMKioskTy5() != null && item.getMKioskTy5().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setAbdominalAorta(item.getMKioskTy6() != null && item.getMKioskTy6().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setGallbladderDisease(item.getMKioskTy7() != null && item.getMKioskTy7().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setBileDuctDisease(item.getMKioskTy8() != null && item.getMKioskTy8().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setNonTraumaticAbdominalEmergency(item.getMKioskTy9() != null && item.getMKioskTy9().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setInfantIntestinalObstruction(item.getMKioskTy10() != null && item.getMKioskTy10().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setEmergencyGastrointestinalEndoscopy(item.getMKioskTy11() != null && item.getMKioskTy11().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setEmergencyGastrointestinalEndoscopyForChildren(item.getMKioskTy12() != null && item.getMKioskTy12().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setEmergencyBronchoscopy(item.getMKioskTy13() != null && item.getMKioskTy13().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setEmergencyBronchoscopyForChildren(item.getMKioskTy14() != null && item.getMKioskTy14().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setLowBirthWeightInfant(item.getMKioskTy15() != null && item.getMKioskTy15().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setObstetricDelivery(item.getMKioskTy16() != null && item.getMKioskTy16().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setObstetricSurgery(item.getMKioskTy17() != null && item.getMKioskTy17().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setEmergencyGynecologicalSurgery(item.getMKioskTy18() != null && item.getMKioskTy18().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setSevereBurns(item.getMKioskTy19() != null && item.getMKioskTy19().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setLimbReattachmentExtremities(item.getMKioskTy20() != null && item.getMKioskTy20().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setLimbReattachmentOther(item.getMKioskTy21() != null && item.getMKioskTy21().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setEmergencyDialysisHD(item.getMKioskTy22() != null && item.getMKioskTy22().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setEmergencyDialysisCRRT(item.getMKioskTy23() != null && item.getMKioskTy23().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setPsychiatry(item.getMKioskTy24() != null && item.getMKioskTy24().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setOphthalmicSurgery(item.getMKioskTy25() != null && item.getMKioskTy25().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setRadiologyVascularIntervention(item.getMKioskTy26() != null && item.getMKioskTy26().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setRadiologyVascularInterventionForChildren(item.getMKioskTy27() != null && item.getMKioskTy27().trim().equals("Y"));
                            emergencyRoomSevereCapacityInfo.setInfantIntestinalAge(item.getMKioskTy10Msg() != null ? item.getMKioskTy10Msg() : "Not Provided");
                            emergencyRoomSevereCapacityInfo.setGastrointestinalEndoscopyAge(item.getMKioskTy12Msg() != null ? item.getMKioskTy12Msg() : "Not Provided");
                            emergencyRoomSevereCapacityInfo.setBronchoscopyAge(item.getMKioskTy14Msg() != null ? item.getMKioskTy14Msg() : "Not Provided");
                            emergencyRoomSevereCapacityInfo.setLowBirthWeightAge(item.getMKioskTy15Msg() != null ? item.getMKioskTy15Msg() : "Not Provided");
                            emergencyRoomSevereCapacityInfo.setRadiologyAge(item.getMKioskTy27Msg() != null ? item.getMKioskTy27Msg() : "Not Provided");
                        }
                    });
                }
            } catch (UnmarshalException unmarshalException) {
                log.error("parsingSevereDiseaseAcceptancePossibleInfo : Response Xml Data 파싱 오류입니다.");
            } catch (IllegalArgumentException illegalArgumentException) {
                log.error("parsingSevereDiseaseAcceptancePossibleInfo : Response Xml Data 이 Null 입니다.");
            } catch (JAXBException e) {
                log.error("parsingSevereDiseaseAcceptancePossibleInfo : JAXBContext 를 생성할 수 없습니다.");
            }
        });

        return totalCount.get();
    }

    /**
     * 응급실 실시간 가용병상정보 조회
     */
    @Transactional
    public void getEmergencyRoomRealTimeAvlBedInfo() {
        log.info("응급실 실시간 가용병상정보 조회 API 요청 시작 : getEmergencyRoomRealTimeAvlBedInfo");
        String url = ApiURLs.EmergencyRoomRealTimeAvlBedInfo.getDefaultUrl(1, numOfRows, serviceKey);
        Optional<String> xmlData = getXmlDataByApi(url);
        int totalCount = parsingEmergencyRoomRealTimeAvlBedInfo(xmlData);
        int nowCount = numOfRows;

        for (int pageNum = 2; nowCount <= totalCount; pageNum++) {
            url = ApiURLs.EmergencyRoomRealTimeAvlBedInfo.getDefaultUrl(pageNum, numOfRows, serviceKey);
            xmlData = getXmlDataByApi(url);
            parsingEmergencyRoomRealTimeAvlBedInfo(xmlData);
            nowCount += numOfRows;
        }
    }

    @Transactional
    private int parsingEmergencyRoomRealTimeAvlBedInfo(Optional<String> xmlData) {
        AtomicInteger totalCount = new AtomicInteger(0);
        xmlData.ifPresent(data -> {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(EmergencyRoomRealTimeAvlBedInfoResponse.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                EmergencyRoomRealTimeAvlBedInfoResponse response = (EmergencyRoomRealTimeAvlBedInfoResponse) unmarshaller.unmarshal(new StringReader(data));

                totalCount.set(response.getBody().getTotalCount());

                List<EmergencyRoomRealTimeAvlBedInfoResponse.Items.Item> itemList = response.getBody().getItems().getItem();
                if (itemList != null && !itemList.isEmpty()) {
                    itemList.forEach(item -> {
                        Hospital hospital = hospitalRepository.findById(item.getHpid())
                                .orElseThrow(() -> {
                                    log.error("기관코드 : {}, 기관이름 : {} 에 해당하는 병원이 존재하지 않습니다.", item.getHpid(), item.getDutyName());
                                    throw new EntityNotFoundException("해당 병원이 존재하지 않습니다.");
                                });

                        Optional<HospitalEquipment> isHospitalEquipmentExist = hospitalEquipmentRepository.findByHospitalDutyId(item.getHpid());
                        if (isHospitalEquipmentExist.isEmpty()) {
                            HospitalEquipment hospitalEquipment = HospitalEquipment.builder()
                                    .hospital(hospital)
                                    .CT(item.getHvctayn() != null && item.getHvctayn().trim().equals("Y"))
                                    .MRI(item.getHvmriayn() != null && item.getHvmriayn().trim().equals("Y"))
                                    .ventilator(item.getHvventiayn() != null && item.getHvventiayn().trim().equals("Y"))
                                    .ventilatorForChildren(item.getHvventisoayn() != null && item.getHvventisoayn().trim().equals("Y"))
                                    .angiographyMachine(item.getHvangioayn() != null && item.getHvangioayn().trim().equals("Y"))
                                    .hyperbaricOxygenTherapyUnit(item.getHvoxyayn() != null && item.getHvoxyayn().trim().equals("Y"))
                                    .centralTemperatureManagementCapable(item.getHvhypoayn() != null && item.getHvhypoayn().trim().equals("Y"))
                                    .ambulance(item.getHvamyn() != null && item.getHvamyn().trim().equals("Y"))
                                    .CRRT(item.getHvcrrtayn() != null && item.getHvcrrtayn().trim().equals("Y"))
                                    .ECMO(item.getHvecmoayn() != null && item.getHvecmoayn().trim().equals("Y"))
                                    .build();
                            hospitalEquipmentRepository.save(hospitalEquipment);
                        } else {
                            HospitalEquipment hospitalEquipment = isHospitalEquipmentExist.get();
                            hospitalEquipment.setCT(item.getHvctayn() != null && item.getHvctayn().trim().equals("Y"));
                            hospitalEquipment.setMRI(item.getHvmriayn() != null && item.getHvmriayn().trim().equals("Y"));
                            hospitalEquipment.setVentilator(item.getHvventiayn() != null && item.getHvventiayn().trim().equals("Y"));
                            hospitalEquipment.setVentilatorForChildren(item.getHvventisoayn() != null && item.getHvventisoayn().trim().equals("Y"));
                            hospitalEquipment.setAngiographyMachine(item.getHvangioayn() != null && item.getHvangioayn().trim().equals("Y"));
                            hospitalEquipment.setHyperbaricOxygenTherapyUnit(item.getHvoxyayn() != null && item.getHvoxyayn().trim().equals("Y"));
                            hospitalEquipment.setCentralTemperatureManagementCapable(item.getHvhypoayn() != null && item.getHvhypoayn().trim().equals("Y"));
                            hospitalEquipment.setAmbulance(item.getHvamyn() != null && item.getHvamyn().trim().equals("Y"));
                            hospitalEquipment.setCRRT(item.getHvcrrtayn() != null && item.getHvcrrtayn().trim().equals("Y"));
                            hospitalEquipment.setECMO(item.getHvecmoayn() != null && item.getHvecmoayn().trim().equals("Y"));
                        }

                        Optional<EmergencyRoom> isEmergencyRoomExist = emergencyRoomRepository.findByHospitalDutyId(item.getHpid());
                        if (isEmergencyRoomExist.isEmpty()) {
                            EmergencyRoom emergencyRoom = EmergencyRoom.builder()
                                    .hospital(hospital)
                                    .name(item.getDutyName() != null ? item.getDutyName() : "No Name")
                                    .phoneNumber(item.getDutyTel3() != null ? item.getDutyTel3() : "No Phone Number")
                                    .pediatricAvailableBeds(item.getHv28() != null ? Integer.parseInt(item.getHv28()) : Integer.MIN_VALUE)
                                    .pediatricStandardBeds(item.getHvs02() != null ? Integer.parseInt(item.getHvs02()) : Integer.MIN_VALUE)
                                    .adultAvailableBeds(item.getHvec() != null ? Integer.parseInt(item.getHvec()) : Integer.MIN_VALUE)
                                    .adultStandardBeds(item.getHvs01() != null ? Integer.parseInt(item.getHvs01()) : Integer.MIN_VALUE)
                                    .apiUpdateTime(LocalDateTime.parse(item.getHvidate(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                                    .build();
                            emergencyRoomRepository.save(emergencyRoom);
                        } else {
                            EmergencyRoom emergencyRoom = isEmergencyRoomExist.get();
                            emergencyRoom.setName(item.getDutyName() != null ? item.getDutyName() : "No Name");
                            emergencyRoom.setPhoneNumber(item.getDutyTel3() != null ? item.getDutyTel3() : "No Phone Number");
                            emergencyRoom.setPediatricAvailableBeds(item.getHv28() != null ? Integer.parseInt(item.getHv28()) : Integer.MIN_VALUE);
                            emergencyRoom.setPediatricStandardBeds(item.getHvs02() != null ? Integer.parseInt(item.getHvs02()) : Integer.MIN_VALUE);
                            emergencyRoom.setAdultAvailableBeds(item.getHvec() != null ? Integer.parseInt(item.getHvec()) : Integer.MIN_VALUE);
                            emergencyRoom.setAdultStandardBeds(item.getHvs01() != null ? Integer.parseInt(item.getHvs01()) : Integer.MIN_VALUE);
                            emergencyRoom.setApiUpdateTime(LocalDateTime.parse(item.getHvidate(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
                        }
                    });
                }
            } catch (UnmarshalException unmarshalException) {
                log.error("parsingEmergencyRoomRealTimeAvlBedInfo : Response Xml Data 파싱 오류입니다.");
            } catch (IllegalArgumentException illegalArgumentException) {
                log.error("parsingEmergencyRoomRealTimeAvlBedInfo : Response Xml Data 이 Null 입니다.");
            } catch (JAXBException e) {
                log.error("parsingEmergencyRoomRealTimeAvlBedInfo : JAXBContext 를 생성할 수 없습니다.");
            }
        });

        return totalCount.get();
    }

    /**
     * 응급실 및 중증질환 메시지 조회
     */
    @Transactional
    public void getEmergencyRoomAndSevereDiseaseMessageInfo() {
        // TODO: 응급실 메시지 조회하고 보내기 전에 end time 지났으면 삭제하고, 안보내는 거 추가해야 함.
        log.info("응급실 및 중증질환 메시지 조회 API 요청 시작 : getEmergencyRoomAndSevereDiseaseMessageInfo");

        String url = ApiURLs.EmergencyMessageInfoResponse.getDefaultUrl(1, 100, serviceKey);
        Optional<String> xmlData = getXmlDataByApi(url);
        int totalCount = parsingEmergencyRoomAndSevereDiseaseMessageInfo(xmlData);
        int nowCount = numOfRows;

        for (int pageNum = 2; nowCount <= totalCount; pageNum++) {
            url = ApiURLs.EmergencyMessageInfoResponse.getDefaultUrl(pageNum, 100, serviceKey);
            xmlData = getXmlDataByApi(url);
            parsingEmergencyRoomAndSevereDiseaseMessageInfo(xmlData);
            nowCount += numOfRows;
        }
    }

    @Transactional
    private int parsingEmergencyRoomAndSevereDiseaseMessageInfo(Optional<String> xmlData) {
        AtomicInteger totalCount = new AtomicInteger(0);
        xmlData.ifPresent(data -> {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

                        JAXBContext jaxbContext = JAXBContext.newInstance(EmergencyMessageInfoResponse.class);
                        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                        EmergencyMessageInfoResponse response = (EmergencyMessageInfoResponse) unmarshaller.unmarshal(new StringReader(data));

                        totalCount.set(response.getBody().getTotalCount());

                        List<EmergencyMessageInfoResponse.Items.Item> itemList = response.getBody().getItems().getItem();
                        if (itemList != null && !itemList.isEmpty()) {
                            itemList.forEach(item -> {
                                Optional<EmergencyMessage> isMessageExist = emergencyMessageRepository.findByEmgMessage(item.getSymBlkMsg());

                                Hospital hospital = hospitalRepository.findById(item.getHpid())
                                        .orElseThrow(() -> {
                                            log.error("기관코드 : {}, 기관이름 : {} 에 해당하는 병원이 존재하지 않습니다.", item.getHpid(), item.getDutyName());
                                            throw new EntityNotFoundException("해당 병원이 존재하지 않습니다.");
                                        });

                                if (isMessageExist.isEmpty()) {
                                    EmergencyMessage emergencyMessage = EmergencyMessage.builder()
                                            .hospital(hospital)
                                            .emgMessage(item.getSymBlkMsg() != null ? item.getSymBlkMsg() : "No Message")
                                            .emgMsgType(item.getSymBlkMsgTyp() != null ? item.getSymBlkMsgTyp() : "No Type")
                                            .emgMsgStartTime(item.getSymBlkSttDtm() != null ? LocalDateTime.parse(item.getSymBlkSttDtm(), formatter) : LocalDateTime.MIN)
                                            .emgMsgEndTime(item.getSymBlkEndDtm() != null ? LocalDateTime.parse(item.getSymBlkEndDtm(), formatter) : LocalDateTime.MAX)
                                            .build();

                                    emergencyMessageRepository.save(emergencyMessage);
                                } else {
                                    EmergencyMessage emergencyMessage = isMessageExist.get();
                                    emergencyMessage.setHospital(hospital);
                                    emergencyMessage.setEmgMessage(item.getSymBlkMsg() != null ? item.getSymBlkMsg() : "No Message");
                                    emergencyMessage.setEmgMsgType(item.getSymBlkMsgTyp() != null ? item.getSymBlkMsgTyp() : "No Type");
                                    emergencyMessage.setEmgMsgStartTime(item.getSymBlkSttDtm() != null ? LocalDateTime.parse(item.getSymBlkSttDtm(), formatter) : LocalDateTime.MIN);
                                    emergencyMessage.setEmgMsgEndTime(item.getSymBlkEndDtm() != null ? LocalDateTime.parse(item.getSymBlkEndDtm(), formatter) : LocalDateTime.MAX);
                                }
                            });
                        }
                    } catch (UnmarshalException unmarshalException) {
                        log.error("parsingEmergencyRoomAndSevereDiseaseMessageInfo : Response Xml Data 파싱 오류입니다.");
                    } catch (IllegalArgumentException illegalArgumentException) {
                        log.error("parsingEmergencyRoomAndSevereDiseaseMessageInfo : Response Xml Data 이 Null 입니다.");
                    } catch (JAXBException e) {
                        log.error("parsingEmergencyRoomAndSevereDiseaseMessageInfo : JAXBContext 를 생성할 수 없습니다.");
                    }
                }
        );

        return totalCount.get();
    }

    private Optional<String> getXmlDataByApi(String url) {
        try {
            // TODO: 지우기
            WebClient webClient = WebClient.create();
            return Optional.ofNullable(webClient.get()
                    .uri(new URI(url))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block());
        } catch (Exception e) {
            // TODO: Exception 종류 찾아보고 예외처리
            e.printStackTrace();
            log.error("Exception occurred while getting XML data from API: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
