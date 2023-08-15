package com.ctrlcv.ersentinel_springboot.data.dto.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 응급의료기관 위치정보 조회
 */
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class EmergencyDeptListInfoByLatLonResponse {
    @XmlElement(name = "header")
    public Header header;
    @XmlElement(name = "body")
    public Body body;

    @Getter
    @Setter
    @XmlRootElement(name = "header")
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    @XmlRootElement(name = "body")
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;
    }

    @Getter
    @Setter
    @XmlRootElement(name = "items")
    public static class Items {
        private List<Item> item;

        @Getter
        @Setter
        @XmlRootElement(name = "item")
        public static class Item {
            private String rnum; // 일련번호
            private String cnt; // 건수
            private String distance; // 거리
            private String dutyAddr; // 주소
            private String dutyDiv; // 병원분류
            private String dutyDivName; // 병원분류명
            private String dutyFax; // 팩스번호
            private String dutyName; // 기관명
            private String dutyTel1; // 대표전화1
            private String startTime; // 시작시간
            private String endTime; // 종료시간
            private String hpid; // 기관ID
            private String latitude; // 위도
            private String longitude; // 경도
        }
    }
}
