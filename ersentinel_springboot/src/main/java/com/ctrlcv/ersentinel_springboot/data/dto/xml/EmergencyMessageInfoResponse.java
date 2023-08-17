package com.ctrlcv.ersentinel_springboot.data.dto.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 응급실 및 중증질환 메시지 조회
 */
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class EmergencyMessageInfoResponse {
    @XmlElement(name = "header")
    private Header header;
    @XmlElement(name = "body")
    private Body body;

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
            private String dutyAddr; // 기관주소
            private String dutyName; // 기관명
            private String emcOrgCod; // 기관코드
            private String hpid; // 기관코드
            private String symBlkMsg; // 전달메시지
            private String symBlkMsgTyp; // 메시지구분
            private String symTypCod; // 중증질환구분
            private String symTypCodMag; // 중증질환명
            private String symOutDspYon; // 중증질환 표출구분
            private String symOutDspMth; // 표출 차단구분
            private String symBlkSttDtm; // 차단시작
            private String symBlkEndDtm; // 차단종료
        }
    }
}
