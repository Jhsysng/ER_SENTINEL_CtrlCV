package com.ctrlcv.ersentinel_springboot.component;

import lombok.Getter;

@Getter
public enum ApiURLs {
    /**
     * 응급의료기관 목록정보 조회
     */
    EmergencyDeptListInfo("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEgytListInfoInqire"),

    /**
     * 응급실 실시간 가용병상정보 조회
     */
    EmergencyRoomRealTimeAvlBedInfo("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire"),

    /**
     * 중증질환자 수용가능정보 조회
     */
    SevereDiseaseAcceptancePossibleInfo("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getSrsillDissAceptncPosblInfoInqire"),

    /**
     * 응급실 및 중증질환 메시지 조회
     */
    EmergencyMessageInfoResponse("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmSrsillDissMsgInqire");

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

    public String getDefaultUrlWithAddr(int pageNum, int numOfRows, String serviceKey, String addr1, String addr2) {
        return url +
                "?serviceKey=" + serviceKey +
                "&pageNo=" + pageNum +
                "&numOfRows=" + numOfRows +
                "&STAGE1=" + addr1 +
                "&STAGE2=" + addr2;
    }

}
