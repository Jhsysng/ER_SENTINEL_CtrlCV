import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "../css/HInfo.css";
import hospitalbed from "../components/hospitalbed.png";
import reset from "../components/reset.png";
import choose from "../components/choose.png";
import location from "../components/location.png";
import call from "../components/call.png";
import greenHInfo from "../components/greenHInfo.png";
import yellowHInfo from "../components/yellowHInfo.png";
import orangeHInfo from "../components/orangeHInfo.png";
import redHInfo from "../components/redHInfo.png";


// Todo: 이거 나중에 지워야함
const Ahospital = {
  error: false,
  data: [
    {
      adultpercent: 80,
      pediatricpercent: 33,
    },
  ],
};

const Bhospital = {
  error: false,
  data: [
    {
      eqlist: [
        "CT",
        "MRI",
        "인공호흡기",
        "인공호흡기조산아",
        "혈관촬영기",
        "CRRT",
        "ECMO",
        "중심체온기",
        "구급차",
      ],
      updateTime: "2023-08-14T23:07:14.825436",
    },
  ],
};

const Chospital = {
  error: false,
  data: [
    {
      ersclist: [
        "심근경색",
        "뇌경색",
        "뇌출혈[거미막하]",
        "뇌출혈[그 외]",
        "대동맥 흉부",
        "대동맥 복부",
        "담낭질환",
        "담도포함질환",
        "복부응급 비외상",
        "위장관 응급내시경 성인",
        "위장관 응급내시경 영유아 (1day)",
        "기관지 응급내시경 성인",
        "산부인과 산과수술",
        "산부인과응급 부인과수술",
        "응급투석 HD",
        "응급투석 CRRT",
        "정신과",
        "안과적수술",
        "영상의학혈관중재 성인",
        "영상의학혈관중재 영유아 (1day)",
      ],
      updateTime: "2023-08-14T23:07:26.432791",
    },
  ],
};

const Dhospital = {
  error: false,
  data: [
    {
      name: "경상국립대학교병원",
      address: "경상남도 진주시 강남로 79 (칠암동)",
      phoneNumber: "055-750-8282",
    },
  ],
};

const Ehospital = {
  error: false,
  data: [
    {
      emgMessage: "소아청소년과 당직의사 부재",
      emgMsgType: "응급",
      lasttime: "2023-08-15T18:00:00",
    },
    {
      emgMessage: "비뇨기과 진료불가",
      emgMsgType: "응급",
      lasttime: "2023-08-15T18:00:00",
    },
    {
      emgMessage: "CRRT 부족",
      emgMsgType: "응급",
      lasttime: "2023-08-15T18:00:00",
    },
    {
      emgMessage: "소아과 진료 불가 (당직의사 부재)",
      emgMsgType: "응급",
      lasttime: "2023-08-16T18:00:00",
    },
  ],
};

// Todo: 이거 나중에 기준 바꿔야 됨
const getCongestionImage = (congestionValue) => {
  if (congestionValue < 50) {
    return greenHInfo;
  } else if (congestionValue < 100) {
    return yellowHInfo;
  } else if (congestionValue < 150) {
    return orangeHInfo;
  } else {
    return redHInfo;
  }
};

const HInfo = () => {
  const { dutyId } = useParams();
  // Todo : 나중에 백 연결하고 이거 주석 풀기
  // const [Ahospital, setAHospital] = useState(null); // 혼잡도
  // const [Bhospital, setBHospital] = useState(null); //
  // const [Chospital, setCHospital] = useState(null);
  // const [Dhospital, setDHospital] = useState(null);
  // const [Ehospital, setEHospital] = useState(null);
  // Todo: 백 Api 연결하기
  const BackAPI = "";

  // Todo: 나중에 백 연결하고 이거 주석 풀기 - 1. 혼잡도
  // useEffect(() => {
  //   const fetchHospitalData = async () => {
  //     try {
  //       const response = await axios.get(`BackAPI/hospital/${dutyID}`);
  //       setAHospital(response.data);
  //     } catch (error) {
  //       console.error("Failed to fetch hospital data", error);
  //     }
  //   };
  //   fetchHospitalData();
  // }, [dutyID]);  // dutyID가 변경될 때마다 백엔드 API 호출

  // Todo: 나중에 백 연결하고 이거 주석 풀기 - 2. 실시간 가용 장비
  // useEffect(() => {
  //   const fetchHospitalData = async () => {
  //     try {
  //       const response = await axios.get(`BackAPI/hospital/${dutyID}`);
  //       setBHospital(response.data);
  //     } catch (error) {
  //       console.error("Failed to fetch hospital data", error);
  //     }
  //   };
  //   fetchHospitalData();
  // }, [dutyID]);  // dutyID가 변경될 때마다 백엔드 API 호출

  // Todo: 나중에 백 연결하고 이거 주석 풀기 - 3. 중증 질환별
  // useEffect(() => {
  //   const fetchHospitalData = async () => {
  //     try {
  //       const response = await axios.get(`BackAPI/hospital/${dutyID}`);
  //       setCHospital(response.data);
  //     } catch (error) {
  //       console.error("Failed to fetch hospital data", error);
  //     }
  //   };
  //   fetchHospitalData();
  // }, [dutyID]);  // dutyID가 변경될 때마다 백엔드 API 호출

  // Todo: 나중에 백 연결하고 이거 주석 풀기 - 4. 길찾기 / 전화하기
  // useEffect(() => {
  //   const fetchHospitalData = async () => {
  //     try {
  //       const response = await axios.get(`BackAPI/hospital/${dutyID}`);
  //       setDHospital(response.data);
  //     } catch (error) {
  //       console.error("Failed to fetch hospital data", error);
  //     }
  //   };
  //   fetchHospitalData();
  // }, [dutyID]);  // dutyID가 변경될 때마다 백엔드 API 호출

  // Todo: 나중에 백 연결하고 이거 주석 풀기 - 5. 공지사항
  // useEffect(() => {
  //   const fetchHospitalData = async () => {
  //     try {
  //       const response = await axios.get(`BackAPI/hospital/${dutyID}`);
  //       setEHospital(response.data);
  //     } catch (error) {
  //       console.error("Failed to fetch hospital data", error);
  //     }
  //   };
  //   fetchHospitalData();
  // }, [dutyID]);  // dutyID가 변경될 때마다 백엔드 API 호출

  if (!Ahospital || !Bhospital || !Chospital || !Dhospital || !Ehospital) {
    return <div>Error: No hospital information available.</div>;
  }

  return (
    <div className="HInfo-hospital-details-container">
      <h1 className="HInfo-hospital-name">{Dhospital.data[0].name}</h1>

      <div className="HInfo-hospital-info-section HInfo-emergency-section">
        <div className="HInfo-emergency-header">
          <div className="HInfo-title-container">
            <h4 className="HInfo-emergency-title">
              현재 응급실 혼잡도(성인 / 소아)
            </h4>
            <span className="HInfo-subtitle">
              현재 환자 수 대비 남은 병실 수 기준입니다.
            </span>
          </div>
          <div className="HInfo-header-right">
            <img
              src={hospitalbed}
              alt="Bed"
              className="HInfo-hospitalbed-icon"
            />
          </div>
        </div>
        <div className="HInfo-congestion-container">
          <div className="HInfo-emergency-content">
            <span className="HInfo-emergency-label">성인 진료</span>
            <img
              src={getCongestionImage(Ahospital.data[0].adultpercent)}
              alt="Adult Congestion"
              className="HInfo-emergency-icon"
            />
            <span className="HInfo-congestion-text">
              성인: {Ahospital.data[0].adultpercent}
            </span>
          </div>
          <div className="HInfo-emergency-content">
            <span className="HInfo-emergency-label">소아 진료</span>
            <img
              src={getCongestionImage(Ahospital.data[0].pediatricpercent)}
              alt="Pediatric Congestion"
              className="HInfo-emergency-icon"
            />
            <span className="HInfo-congestion-text">
              소아: {Ahospital.data[0].pediatricpercent}
            </span>
          </div>
        </div>
      </div>

      <div className="HInfo-hospital-info-section HInfo-procedure-section">
        <div className="HInfo-procedure-header">
          <div className="HInfo-title-container">
            <h4 className="HInfo-procedure-title">실시간 가용장비 정보</h4>
            <span className="HInfo-subtitle">
              해당 정보는 10분마다 갱신됩니다.
            </span>
            <span className="HInfo-update-time">
              갱신시각: {Bhospital.data[0].updateTime}
            </span>
          </div>
          <div className="HInfo-header-right">
            <img src={reset} alt="Update" className="HInfo-reset-icon" />
          </div>
        </div>
        <div className="HInfo-congestion-container">
          <div className="HInfo-procedure-content">
            <div className="HInfo-procedure-list-container">
              <ul className="HInfo-procedure-list-left">
                {Bhospital.data[0].eqlist.map((data, index) => (
                  <li key={index} className="HInfo-procedure-item">
                    {data}
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>

      <div className="HInfo-hospital-info-section HInfo-procedure-section">
        <div className="HInfo-procedure-header">
          <div className="HInfo-title-container">
            <h4 className="HInfo-procedure-title">
              중증 질환별 수용 가능 여부
            </h4>
            <span className="HInfo-subtitle">
              해당 정보는 20분마다 갱신됩니다.
            </span>
            <span className="HInfo-update-time">
              갱신시각: {Chospital.data[0].updateTime}
            </span>
          </div>
          <div className="HInfo-header-right">
            <img src={reset} alt="Update" className="HInfo-reset-icon" />
          </div>
        </div>
        <div className="HInfo-congestion-container">
          <div className="HInfo-procedure-content">
            <div className="HInfo-procedure-list-container">
              <ul className="HInfo-procedure-list-left">
                {Chospital.data[0].ersclist.map((data, index) => (
                  <li key={index} className="HInfo-procedure-item">
                    {data}
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>

      <div className="HInfo-hospital-info-section HInfo-procedure-section">
        <div className="HInfo-procedure-header">
          <div className="HInfo-title-container">
            <h4 className="HInfo-procedure-title">응급실 길찾기 / 전화하기</h4>
            <span className="HInfo-subtitle">
              아이콘을 누르면 곧바로 연결됩니다.
            </span>
          </div>
          <div className="HInfo-header-right">
            <img src={choose} alt="Update" className="HInfo-reset-icon" />
          </div>
        </div>
        <div className="HInfo-congestion-container">
          <div className="HInfo-procedure-content">
            <div className="HInfo-procedure-list-container">
              <ul className="HInfo-procedure-list-left">
                <li className="HInfo-procedure-item">
                  주소: {Dhospital.data[0].address}
                  <a
                    href={`https://www.google.com/maps/dir/?api=1&destination=${Dhospital.data[0].address}`}
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    <img
                      src={location}
                      alt="Location Icon"
                      className="HInfo-info-icon"
                    />
                  </a>
                </li>
                <li className="HInfo-procedure-item">
                  전화번호:{" "}
                  <a href={`tel:${Dhospital.data[0].phoneNumber}`}>
                    {Dhospital.data[0].phoneNumber}
                  </a>
                  <a href={`tel:${Dhospital.data[0].phoneNumber}`}>
                    <img
                      src={call}
                      alt="Call Icon"
                      className="HInfo-info-icon"
                    />
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>

      <div className="HInfo-hospital-info-section HInfo-procedure-section">
        <div className="HInfo-procedure-header">
          <div className="HInfo-title-container">
            <h4 className="HInfo-procedure-title">공지사항</h4>
            <span className="HInfo-subtitle">
              해당 정보는 60분마다 갱신됩니다.
            </span>
            <span className="HInfo-update-time">
              갱신시각: {Ehospital.data[Ehospital.data.length - 1].lasttime}
            </span>
          </div>
          <div className="HInfo-header-right">
            <img src={reset} alt="Update" className="HInfo-reset-icon" />
          </div>
        </div>
        <div className="HInfo-congestion-container">
          <div className="HInfo-procedure-content">
            <div className="HInfo-procedure-list-container">
              <ul className="HInfo-procedure-list-left">
                {Ehospital.data.map((item, index) => (
                  <li key={index} className="HInfo-procedure-item">
                    {item.emgMessage}
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HInfo;