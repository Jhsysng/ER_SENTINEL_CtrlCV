import React from "react";
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

const hospital = {
  id: 1,
  name: "Gangnam General Hospital",
  address: "123 Gangnam-daero, Gangnam-gu, Seoul",
  location: "123 Gangnam-daero, Gangnam-gu, Seoul",
  phone: "02-1234-5678",
  Acongestion: 1,
  Pcongestion: 8,
  procedures: [
    "뇌출혈",
    "뇌경색의재관류",
    "심근경색의재관류",
    "복부손상의수술",
    "사지접합의수술",
    "응급내시경",
    "응급투석",
    "조산산모",
    "신생아",
    "중증화상",
  ],
  Available_Beds: [
    "응급실성인병상",
    "응급실소아병상",
    "수술실",
    "신경과중환자실",
    "신생아중환자실",
    "흉부외과중환자실",
    "일반중환자실",
    "내과중환자실",
    "외과중환자실",
    "신경외과중환자실",
    "CT",
    "MRI",
    "혈관촬영기",
    "인공호흡기",
    "CRRT",
  ],
  ERS: [
    "심근경색증",
    "중증화상",
    "허혈성뇌졸중",
    "조산아",
    "출혈성뇌졸중",
    "저체중아",
    "대동맥박리",
    "간질지속상태",
    "폐혈증",
    "뇌수막염",
  ],
  pro_lastUpdated: "2023-08-08 14:30",
  beds_lastUpdated: "2023-08-07 13:30",
  Notice: [
    "응급실 진료 순서는 중증도 분류 체계에 따라 신속한 치료가 필요한 환자 우선입니다.",
    "심혈관질환 전용 핫라인: 010-XXXX-XXXX",
    "소아외과 부재로 수용불가능, 이송 및 전원 시 참고바랍니다.",
  ],
};

const getCongestionImage = (congestionValue) => {
  if (congestionValue < 5) {
    return greenHInfo;
  } else if (congestionValue < 10) {
    return yellowHInfo;
  } else if (congestionValue < 15) {
    return orangeHInfo;
  } else {
    return redHInfo;
  }
};

const HInfo = () => {
  if (!hospital) {
    return <div>Error: No hospital information available.</div>;
  }

  return (
    <div className="HInfo-hospital-details-container">
      <h1 className="HInfo-hospital-name">{hospital.name}</h1>

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
              src={getCongestionImage(hospital.Acongestion)}
              alt="Adult Congestion"
              className="HInfo-emergency-icon"
            />
            <span className="HInfo-congestion-text">
              성인: {hospital.Acongestion}
            </span>
          </div>
          <div className="HInfo-emergency-content">
            <span className="HInfo-emergency-label">소아 진료</span>
            <img
              src={getCongestionImage(hospital.Pcongestion)}
              alt="Pediatric Congestion"
              className="HInfo-emergency-icon"
            />
            <span className="HInfo-congestion-text">
              소아: {hospital.Pcongestion}
            </span>
          </div>
        </div>
      </div>

      <div className="HInfo-hospital-info-section HInfo-procedure-section">
        <div className="HInfo-procedure-header">
          <div className="HInfo-title-container">
            <h4 className="HInfo-procedure-title">
              시술명 기준 현재 가능 응급의
            </h4>
            <span className="HInfo-subtitle">
              해당 정보는 20분마다 갱신됩니다.
            </span>
            <span className="HInfo-update-time">
              갱신시각: {hospital.pro_lastUpdated}
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
                {hospital.procedures
                  .slice(0, Math.ceil(hospital.procedures.length))
                  .map((procedure, index) => (
                    <li key={index} className="HInfo-procedure-item">
                      {procedure}
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
              실시간 가용병상 및 가용장비 정보
            </h4>
            <span className="HInfo-subtitle">
              해당 정보는 10분마다 갱신됩니다.
            </span>
            <span className="HInfo-update-time">
              갱신시각: {hospital.beds_lastUpdated}
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
                {hospital.Available_Beds.map((bed, index) => (
                  <li key={index} className="HInfo-procedure-item">
                    {bed}
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
              갱신시각: {hospital.beds_lastUpdated}
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
                {hospital.ERS.map((bed, index) => (
                  <li key={index} className="HInfo-procedure-item">
                    {bed}
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
                  주소: {hospital.address}
                  <a
                    href={`https://www.google.com/maps/dir/?api=1&destination=${hospital.address}`}
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
                  <a href={`tel:${hospital.phone}`}>{hospital.phone}</a>
                  <a href={`tel:${hospital.phone}`}>
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
              갱신시각: {hospital.beds_lastUpdated}
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
                {hospital.Notice.map((bed, index) => (
                  <li key={index} className="HInfo-procedure-item">
                    {bed}
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
