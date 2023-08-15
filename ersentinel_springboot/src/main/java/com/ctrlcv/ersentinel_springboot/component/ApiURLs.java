package com.ctrlcv.ersentinel_springboot.component;

import lombok.Getter;

@Getter
public enum ApiURLs {
    /**
     * 응급의료기관 목록정보 조회
     */
    EmergencyDeptListInfo("http://27.101.215.194/B552657/ErmctInfoInqireService/getEgytListInfoInqire"),

    /**
     * 응급의료기관 위치정보 조회
     */
    EmergencyDeptListByLonLatInfo("http://27.101.215.194/B552657/ErmctInfoInqireService/getEgytLcinfoInqire"),

    /**
     * 응급실 실시간 가용병상정보 조회
     */
    EmergencyRoomRealTimeAvlBedInfo("http://27.101.215.194/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire"),

    /**
     * 중증질환자 수용가능정보 조회
     */
    SevereDiseaseAcceptancePossibleInfo("http://27.101.215.194/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire"),

    /**
     * 응급실 및 중증질환 메시지 조회
     */
    EmergencyMessageInfoResponse("http://27.101.215.194/B552657/ErmctInfoInqireService/getEmrrmSrsillDissMsgInqire");

    private final String url;
    ApiURLs(String url) {
        this.url = url;
    }

    public String getDefaultUrl(int pageNum, int numOfRows, String serviceKey) {
        return url +
                "?serviceKey=" + serviceKey +
                "&pageNo=" + pageNum +
                "&numOfRows=" + numOfRows;
    }

    public String getDefaultUrlWithLonLat(int pageNum, int numOfRows, String serviceKey, double Lon, double Lat) {
        return url +
                "?serviceKey=" + serviceKey +
                "&pageNo=" + pageNum +
                "&numOfRows=" + numOfRows +
                "&WGS84_LON=" + Lon +
                "&WGS84_LAT=" + Lat;
    }

}
