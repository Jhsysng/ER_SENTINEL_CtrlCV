package com.ctrlcv.ersentinel_springboot.data.dto.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 응급의료기관 목록정보 조회
 */
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class EmergencyDeptListInfoResponse {
    @XmlElement(name = "header")
    protected Header header;
    @XmlElement(name = "body")
    protected Body body;

    @Getter
    @Setter
    @XmlRootElement(name = "header")
    protected static class Header {
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
            private String hpid; // 기관ID
            private String phpid; // 기관ID(OLD)
            private String dutyEmcls; // 응급의료기관분류
            private String dutyEmclsName; // 응급의료기관분류명
            private String dutyAddr; // 주소
            private String dutyName; // 기관명
            private String dutyTel1; // 대표전화1
            private String dutyTel3; // 응급실전화
            private String wgs84Lon; // 병원경도
            private String wgs84Lat; // 병원위도
        }
    }
}
