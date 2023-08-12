package com.ctrlcv.ersentinel_springboot.data.dto.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 중증질환자 수용가능정보 조회
 */
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class SevereDiseaseAcceptancePosblInfoResponse {
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
            private String dutyName; // 기관명
            private String hpid; // 기관ID
            private String MKioskTy28; // 응급실(Emergency gate keeper)
            private String MKioskTy1; // [재관류중재술] 심근경색
            private String MKioskTy2; // [재관류중재술] 뇌경색
            private String MKioskTy3; // [뇌출혈수술] 거미막하출혈
            private String MKioskTy4; // [뇌출혈수술] 거미막하출혈 외
            private String MKioskTy5; // [대동맥응급] 흉부
            private String MKioskTy6; // [대동맥응급] 복부
            private String MKioskTy7; // [담낭담관질환] 담낭질환
            private String MKioskTy8; // [담낭담관질환] 담도포함질환
            private String MKioskTy9; // [복부응급수술] 비외상
            private String MKioskTy10; // [장중첩/폐색] 영유아
            private String MKioskTy11; // [응급내시경] 성인 위장관
            private String MKioskTy12; // [응급내시경] 영유아 위장관
            private String MKioskTy13; // [응급내시경] 성인 기관지
            private String MKioskTy14; // [응급내시경] 영유아 기관지
            private String MKioskTy15; // [저출생체중아] 집중치료
            private String MKioskTy16; // [산부인과응급] 분만
            private String MKioskTy17; // [산부인과응급] 산과수술
            private String MKioskTy18; // [산부인과응급] 부인과수술
            private String MKioskTy19; // [중증화상] 전문치료
            private String MKioskTy20; // [사지접합] 수족지접합
            private String MKioskTy21; // [사지접합] 수족지접합 외
            private String MKioskTy22; // [응급투석] HD
            private String MKioskTy23; // [응급투석] CRRT
            private String MKioskTy24; // [정신과적응급] 폐쇄병동입원
            private String MKioskTy25; // [안과적수술] 응급
            private String MKioskTy26; // [영상의학혈관중재] 성인
            private String MKioskTy27; // [영상의학혈관중재] 영유아
            private String MKioskTy10Msg; // 장중첩/폐색(영유아) 가능연령
            private String MKioskTy12Msg; // 위장관 응급내시경(영유아) 가능연령
            private String MKioskTy14Msg; // 기관지 응급내시경(영유아) 가능연령
            private String MKioskTy15Msg; // 저출생 체중아 가능연령
            private String MKioskTy27Msg; // 영상의학 혈관 중재적 시술(영유아) 가능연령
        }

    }
}
