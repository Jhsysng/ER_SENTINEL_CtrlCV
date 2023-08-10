import React from "react";
import "../css/HInfo.css";
import hospitalbed from "../components/hospitalbed.png";
import reset from "../components/reset.png";
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

const HInfo = (props) => {
  // props.location.state로부터 hospital 정보 얻어오기
  // const hospital = (props.location && props.location.state) ? props.location.state.hospital : null;

  // hospital 값이 null 또는 undefined인 경우 기본 화면 또는 에러 메시지를 반환
  if (!hospital) {
    return <div>Error: No hospital information available.</div>;
  }

  return (
    <div className="hospital-details-container">
      <h1 className="hospital-name">{hospital.name}</h1>

      <div className="hospital-info-section emergency-section">
        <img src={hospitalbed} alt="Bed" className="hospitalbed-icon" />
        <div className="info-content">
          <h4 className="emergency-title">현재 응급실 혼잡도</h4>
          <div className="emergency-content">
            <span className="emergency-label">성인 진료</span>
            <img
              src={getCongestionImage(hospital.Acongestion)}
              alt="Adult Congestion"
              className="emergency-icon"
            />
            <span>성인: {hospital.Acongestion}</span>
          </div>
          <div className="emergency-content">
            <span className="emergency-label">소아 진료</span>
            <img
              src={getCongestionImage(hospital.Pcongestion)}
              alt="Pediatric Congestion"
              className="emergency-icon"
            />
            <span>소아: {hospital.Pcongestion}</span>
          </div>
        </div>
      </div>

      <div className="hospital-info-section">
        <img src={reset} alt="Location" className="info-icon" />
        <div className="info-content">
          <h3>Location</h3>
          <p>{hospital.location}</p>
        </div>
      </div>

      <div className="hospital-info-section">
        <img src={reset} alt="Location" className="info-icon" />
        <div className="info-content">
          <h3>Location</h3>
          <p>{hospital.location}</p>
        </div>
      </div>

      <div className="hospital-info-section">
        <img src={reset} alt="Location" className="info-icon" />
        <div className="info-content">
          <h3>Location</h3>
          <p>{hospital.location}</p>
        </div>
      </div>

      <div className="hospital-info-section">
        <img src={reset} alt="Location" className="info-icon" />
        <div className="info-content">
          <h3>Location</h3>
          <p>{hospital.location}</p>
        </div>
      </div>

      <div className="hospital-info-section">
        <img src={reset} alt="Location" className="info-icon" />
        <div className="info-content">
          <h3>Location</h3>
          <p>{hospital.location}</p>
        </div>
      </div>
    </div>
  );
};

export default HInfo;
