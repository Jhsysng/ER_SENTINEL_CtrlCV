package com.ctrlcv.ersentinel_springboot.data.dto.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 응급실 실시간 가용병상정보 조회
 */
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class EmergencyRoomRealTimeAvlBedInfoResponse {
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
            private String rnum;    // 일련번호
            private String hpid;    // 기관코드
            private String phpid;   // 기관코드
            private String hvidate; // 입력일시
            private String hvec;    // 일반
            private String hvoc;    // [기타] 수술실
            private String hvcc;    // [중환자실] 신경과
            private String hvncc;   // [중환자실] 신생아
            private String hvccc;   // [중환자실] 흉부외과
            private String hvicc;   // [중환자실] 일반
            private String hvgc;    // [입원실] 일반
            private String hvdnm;   // 당직의
            private String hvctayn; // CT가용(가/부)
            private String hvmriayn; // MRI가용(가/부)
            private String hvangioayn; // 혈관촬영기가용(가/부)
            private String hvventiayn; // 인공호흡기가용(가/부)
            private String hvventisoayn; // 인공호흡기 조산아가용(가/부)
            private String hvincuayn; // 인큐베이터가용(가/부)
            private String hvcrrtayn; // CRRT가용(가/부)
            private String hvecmoayn; // ECMO가용(가/부)
            private String hvoxyayn; // 고압산소치료기가용(가/부)
            private String hvhypoayn; // 중심체온조절유도기(가/부)
            private String hvamyn; // 구급차가용여부
            private String hv1; // 응급실 당직의 직통연락처
            private String hv2; // [중환자실] 내과
            private String hv3; // [중환자실] 외과
            private String hv4; // 외과입원실(정형외과)
            private String hv5; // 신경과입원실
            private String hv6; // [중환자실] 신경외과
            private String hv7; // 약물중환자
            private String hv8; // [중환자실] 화상
            private String hv9; // [중환자실] 외상
            private String hv10; // VENTI(소아)
            private String hv11; // 인큐베이터(보육기)
            private String hv12; // 소아당직의 직통연락처
            private String hv13; // 격리진료구역 음압격리병상
            private String hv14; // 격리진료구역 일반격리병상
            private String hv15; // 소아음압격리
            private String hv16; // 소아일반격리
            private String hv17; // [응급전용] 중환자실 음압격리
            private String hv18; // [응급전용] 중환자실 일반격리
            private String hv19; // [응급전용] 입원실 음압격리
            private String hv21; // [응급전용] 입원실 일반격리
            private String hv22; // 감염병 전담병상 중환자실
            private String hv23; // 감염병 전담병상 중환자실 내 음압격리병상
            private String hv24; // [감염] 중증 병상
            private String hv25; // [감염] 준-중증 병상
            private String hv26; // [감염] 중등증 병상
            private String hv27; // 코호트 격리
            private String hv28; // 소아
            private String hv29; // 응급실 음압 격리 병상
            private String hv30; // 응급실 일반 격리 병상
            private String hv31; // [응급전용] 중환자실
            private String hv32; // [중환자실] 소아
            private String hv33; // [응급전용] 소아중환자실
            private String hv34; // [중환자실] 심장내과
            private String hv35; // [중환자실] 음압격리
            private String hv36; // [응급전용] 입원실
            private String hv37; // [응급전용] 소아입원실
            private String hv38; // [입원실] 외상전용
            private String hv39; // [기타] 외상전용 수술실
            private String hv40; // [입원실] 정신과 폐쇄병동
            private String hv41; // [입원실] 음압격리
            private String hv42; // [기타] 분만실
            private String hv43; // [기타] 화상전용처치실
            private String dutyName; // 기관명
            private String dutyTel3; // 응급실전화
            private String hvs01; // 일반_기준
            private String hvs02; // 소아_기준
            private String hvs03; // 응급실 음압 격리 병상_기준
            private String hvs04; // 응급실 일반 격리 병상_기준
            private String hvs05; // [응급전용] 중환자실_기준
            private String hvs06; // [중환자실] 내과_기준
            private String hvs07; // [중환자실] 외과_기준
            private String hvs08; // [중환자실] 신생아_기준
            private String hvs09; // [중환자실] 소아_기준
            private String hvs10; // [응급전용] 소아중환자실_기준
            private String hvs11; // [중환자실] 신경과_기준
            private String hvs12; // [중환자실] 신경외과_기준
            private String hvs13; // [중환자실] 화상_기준
            private String hvs14; // [중환자실] 외상_기준
            private String hvs15; // [중환자실] 심장내과_기준
            private String hvs16; // [중환자실] 흉부외과_기준
            private String hvs17; // [중환자실] 일반_기준
            private String hvs18; // [중환자실] 음압격리_기준
            private String hvs19; // [응급전용] 입원실_기준
            private String hvs20; // [응급전용] 소아입원실_기준
            private String hvs21; // [입원실] 외상전용_기준
            private String hvs22; // [기타] 수술실_기준
            private String hvs23; // [기타] 외상전용 수술실_기준
            private String hvs24; // [입원실] 정신과 폐쇄병동_기준
            private String hvs25; // [입원실] 음압격리_기준
            private String hvs26; // [기타] 분만실_기준
            private String hvs27; // CT_기준
            private String hvs28; // MRI_기준
            private String hvs29; // 혈관촬영기_기준
            private String hvs30; // 인공호흡기 일반_기준
            private String hvs31; // 인공호흡기 조산아_기준
            private String hvs32; // 인큐베이터_기준
            private String hvs33; // CRRT_기준
            private String hvs34; // ECMO_기준
            private String hvs35; // 중심체온조절유도기_기준
            private String hvs36; // [기타] 화상전용처치실_기준
            private String hvs37; // 고압산소치료기_기준
            private String hvs38; // [입원실] 일반_기준
            private String hvs46; // 격리진료구역 음압격리_기준
            private String hvs47; // 격리진료구역 일반격리_기준
            private String hvs48; // 소아음압격리_기준
            private String hvs49; // 소아일반격리_기준
            private String hvs50; // [응급전용] 중환자실 음압격리_기준
            private String hvs51; // [응급전용] 중환자실 일반격리_기준
            private String hvs52; // [응급전용] 입원실 음압격리_기준
            private String hvs53; // [응급전용] 입원실 일반격리_기준
            private String hvs54; // 감염병 전담병상 중환자실_기준
            private String hvs55; // 감염병 전담병상 중환자실 내 음압격리병상_기준
            private String hvs56; // [감염] 중증 병상_기준
            private String hvs57; // [감염] 준-중증 병상_기준
            private String hvs58; // [감염] 중등증 병상_기준
            private String hvs59; // 코호트 격리_기준
        }

    }
}
