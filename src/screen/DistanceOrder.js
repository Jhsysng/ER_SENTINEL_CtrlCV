import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "../css/DistanceOrder.css";
import axios from "axios";

// Todo: 이거 나중에 지우기
const sampleHospitals = [
  {
    id: 1,
    dutyId: "A000001",
    name: "중앙대학교병원",
    distance: "3.1",
    adultpercent: "33",
    pediatricpercent: "0",
  },
  {
    id: 2,
    dutyId: "A000002",
    name: "이화여자대학교의과대학부속목동병원",
    distance: "2.3",
    adultpercent: "52",
    pediatricpercent: "0",
  },
  {
    id: 3,
    dutyId: "A000003",
    name: "국립중앙의료원",
    distance: "1.7",
    adultpercent: "13",
    pediatricpercent: "0",
  },
  {
    id: 4,
    dutyId: "A000004",
    name: "한양대학교병원",
    distance: "5.4",
    adultpercent: "22",
    pediatricpercent: "25",
  },
  {
    id: 5,
    dutyId: "A000005",
    name: "가톨릭대학교은평성모병원",
    distance: "4.2",
    adultpercent: "45",
    pediatricpercent: "25",
  },
  {
    id: 6,
    dutyId: "A000006",
    name: "한림대학교강남성심병원",
    distance: "3.2",
    adultpercent: "25",
    pediatricpercent: "0",
  },
  {
    id: 7,
    dutyId: "A000007",
    name: "학교법인고려중앙학원고려대학교의과대학부속병원(안암병원)",
    distance: "5.7",
    adultpercent: "23",
    pediatricpercent: "0",
  },
];

const DistanceOrder = () => {
  const [sortedHospitals, setSortedHospitals] = useState([]);
  const [userLocation, setUserLocation] = useState("서울 종로구 종로6가 70");
  const [Latitude, setLatitude] = useState("");
  const [Longitude, setLongitude] = useState("");
  const Navigate = useNavigate();

  const handleHospitalClick = (hospitalCode) => {
    // Navigate(`/HInfo`, {state: {dutyId: A000001}});
  };

  const requestPermissionAgain = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        async (position) => {
          const latitude = position.coords.latitude;
          const longitude = position.coords.longitude;
          setLatitude(latitude);
          setLongitude(longitude);
          try {
            const response = await fetch(
              `https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${longitude}&y=${latitude}`,
              {
                headers: {
                  Authorization: "KakaoAK e66ae55f0df11d36b2b954ae39f001eb",
                  "Content-Type":
                    "application/x-www-form-urlencoded;charset=utf-8",
                },
              }
            );
            const data = await response.json();
            if (data.documents && data.documents.length > 0) {
              const address = data.documents[0].address;
              setUserLocation(address.address_name);
            } else {
              setUserLocation("Cannot find address for this location");
            }
          } catch (error) {
            setUserLocation("Error fetching address");
          }
        },
        (error) => {
          if (error.code === error.PERMISSION_DENIED) {
            setUserLocation("User denied the request for Geolocation.");
          } else {
            setUserLocation("Unable to retrieve your location");
          }
        }
      );
    } else {
      setUserLocation("Geolocation is not supported by this browser");
    }
  };

  useEffect(() => {
    requestPermissionAgain(); // 초기 로드 시 권한 요청

    /* Todo: 백 연결 하고 주석 부분 풀기
    if (userLocation !== "Fetching location...") {
      axios.post(BackAPI, { lon: Latitude, lat: Longitude })
        .then(response => {
          setSortedHospitals(response.data.hospitals);
        })
        .catch(error => {
          console.error("Error fetching hospitals:", error);
        });
    }
    */

    // Todo: 백 연결하고 이거 바꿔주기 distance 없음
    const sorted = [...sampleHospitals].sort(
      (a, b) => parseFloat(a.distance) - parseFloat(b.distance)
    );
    setSortedHospitals(sorted);
  }, [userLocation]);

  return (
    <div className="Distance-distance-container">
      <h2 className="Distance-h2">{userLocation}</h2>
      {/*{userLocation === "User denied the request for Geolocation." && (*/}
      {/*  <div>*/}
      {/*    <p>Please allow location access to use this feature.</p>*/}
      {/*    <button onClick={requestPermissionAgain}>*/}
      {/*      Request Permission Again*/}
      {/*    </button>*/}
      {/*  </div>*/}
      {/*)}*/}
      <ul className="Distance-hospital-list">
        {sortedHospitals.map((hospital) => (
          <li
            key={hospital.id}
            className="Distance-hospital-item"
            onClick={() => handleHospitalClick(hospital.dutyId)}
          >
            <span className="Distance-hospital-name">{hospital.name}</span>
            <div className="Distance-congestion-info">
              <span>성인: {hospital.adultpercent}</span>
            </div>
            <div className="Distance-congestion-info">
              <span>소아: {hospital.pediatricpercent}</span>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default DistanceOrder;
