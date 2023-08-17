import React, { useContext, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Cookies } from "react-cookie";
import { parseJwt } from "../utils/JwtUtils";
import { config } from "../utils/Constants";
import axios from "axios";
import "../css/HInfo.css";
import "../utils/ApiUtils.js";
import AuthContext from "./AuthContext";

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

const AppHManager = () => {
  // AuthContext : 전역적인 변수.
  // Auth.getUser().accessToken : 토큰을 가져오는 것
  // header에 Authorization: Bearer {accessToken} 이거 넣어주기.

  //   useEffect(() => {
  //     if (Auth.getUser().data.role !== "MANAGER") {
  //         alert("Only the manager has access!!!!");
  //         Navigate('/Main');
  //     };
  //   }, []);

  const Auth = useContext(AuthContext);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

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
  // }, []);  // dutyID가 변경될 때마다 백엔드 API 호출

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
  // }, []);  // dutyID가 변경될 때마다 백엔드 API 호출

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
  // }, []);  // dutyID가 변경될 때마다 백엔드 API 호출

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
  // }, []);  // dutyID가 변경될 때마다 백엔드 API 호출

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
  // }, []);  // dutyID가 변경될 때마다 백엔드 API 호출

  // Todo: 나중에 백 연결하고 이거 주석 풀기 - 5. 공지사항
  // useEffect(() => {
  // setIsLoggedIn(Auth.userIsAuthenticated());
  //   const fetchHospitalData = async () => {
  //     try {
  //       const response = await axios.get(`BackAPI/hospital/${dutyID}`);
  //       setEHospital(response.data);
  //     } catch (error) {
  //       console.error("Failed to fetch hospital data", error);
  //     }
  //   };
  //   fetchHospitalData();
  // }, []);  // dutyID가 변경될 때마다 백엔드 API 호출

  //   useEffect(() => {
  //     const DeleteData = async () => {
  //       try {
  //         const response = await axios.delete(`BackAPI/hospital/${userID}`);
  //         setAHospital(response.data);
  //       } catch (error) {
  //         console.error("Failed to delete hospital data", error);
  //       }
  //     };
  //     DeleteData();
  //   }, []);  // dutyID가 변경될 때마다 백엔드 API 호출

  //   const DeleteData = async () => {
  //     try {
  //       const response = await axios.delete(`BackAPI/hospital/${userID}`);
  //       setAHospital(response.data);
  //     } catch (error) {
  //       console.error("Failed to delete hospital data", error);
  //     }
  //   };

  return (
    <div className="HInfo-hospital-details-container">
      <h1 className="HInfo-hospital-name">Hosp Manager</h1>

      <div className="HInfo-hospital-info-section HInfo-procedure-section">
        <div className="HInfo-procedure-header">
          <div className="HInfo-title-container">
            <h4 className="HInfo-procedure-title">실시간 가용장비 정보</h4>
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
              <div className="HInfo-procedure-list">
                <button>삭제하기</button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="HInfo-hospital-info-section HInfo-procedure-section">
        <div className="HInfo-procedure-header">
          <div className="HInfo-title-container">
            <h4 className="HInfo-procedure-title">실시간 가용장비 정보</h4>
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
            <h4 className="HInfo-procedure-title">실시간 가용장비 정보</h4>
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
            <h4 className="HInfo-procedure-title">실시간 가용장비 정보</h4>
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
    </div>
  );
};

export default AppHManager;
