import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import "../css/DistanceOrder.css";
import axios from "axios";

const sampleHospitals = [
  {
    id: 1,
    name: "Hospital 1",
    distance: "3.1",
    Acongestion: "2",
    Bcongestion: "2",
  },
  {
    id: 2,
    name: "Hospital 2",
    distance: "2.3",
    Acongestion: "1",
    Bcongestion: "3",
  },
  {
    id: 3,
    name: "Hospital 3",
    distance: "1.7",
    Acongestion: "4",
    Bcongestion: "6",
  },
  {
    id: 4,
    name: "Hospital 4",
    distance: "5.4",
    Acongestion: "3",
    Bcongestion: "4",
  },
  {
    id: 5,
    name: "Hospital 5",
    distance: "4.2",
    Acongestion: "4",
    Bcongestion: "7",
  },
  {
    id: 6,
    name: "Hospital 6",
    distance: "3.2",
    Acongestion: "5",
    Bcongestion: "3",
  },
  {
    id: 7,
    name: "Hospital 7",
    distance: "5.7",
    Acongestion: "6",
    Bcongestion: "2",
  },
  {
    id: 8,
    name: "Hospital 8",
    distance: "2.3",
    Acongestion: "2",
    Bcongestion: "4",
  },
  {
    id: 9,
    name: "Hospital 9",
    distance: "6.7",
    Acongestion: "7",
    Bcongestion: "7",
  },
  {
    id: 10,
    name: "Hospital 10",
    distance: "8.2",
    Acongestion: "8",
    Bcongestion: "3",
  },
  {
    id: 11,
    name: "Hospital 11",
    distance: "9.1",
    Acongestion: "2",
    Bcongestion: "4",
  },
  {
    id: 12,
    name: "Hospital 12",
    distance: "0.5",
    Acongestion: "1",
    Bcongestion: "3",
  },
  // ... 나머지 병원 데이터
];

const DistanceOrder = () => {
  const [sortedHospitals, setSortedHospitals] = useState([]);
  const [userLocation, setUserLocation] = useState("Fetching location...");

  useEffect(() => {
    // 사용자 위치 가져오기
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        async (position) => {
          const latitude = position.coords.latitude;
          const longitude = position.coords.longitude;
          try {
            const response = await fetch(
              `https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${longitude}&y=${latitude}`,
              {
                headers: {
                  Authorization: "KakaoAK e66ae55f0df11d36b2b954ae39f001eb", // 'KakaoAK'를 사용하여 API 키를 지정합니다.
                  "Content-Type":
                    "application/x-www-form-urlencoded;charset=utf-8", // 이 헤더도 추가합니다.
                },
              }
            );
            const data = await response.json();
            console.log(data);
            if (data.documents && data.documents.length > 0) {
              const address = data.documents[0].address;

              // const addressComponents = address.address_name.split(" ");
              // const first_address = addressComponents[0];
              // const second_address = addressComponents[1];
              // DB 연결 및 데이터 전송 로직
              // axios
              //   .post("YOUR_DB_ENDPOINT", {
              //     first_address: first_address,
              //     second_address: second_address,
              //   })
              //   .then((response) => {
              //     console.log(response.data); // 성공적으로 데이터를 보냈을 때의 응답을 출력
              //   })
              //   .catch((error) => {
              //     console.log("Error sending data to DB: ", error);
              //   });

              setUserLocation(address.address_name);
            } else {
              setUserLocation("Cannot find address for this location");
            }
          } catch (error) {
            setUserLocation("Error fetching address");
          }
        },
        () => {
          setUserLocation("Unable to retrieve your location");
        }
      );
    } else {
      setUserLocation("Geolocation is not supported by this browser");
    }

    const sorted = [...sampleHospitals].sort(
      (a, b) => parseFloat(a.distance) - parseFloat(b.distance)
    );
    setSortedHospitals(sorted);
  }, []);

  return (
    <div className="Distance-distance-container">
      <h2 className="Distance-h2">{userLocation}</h2>

      <ul className="Distance-hospital-list">
        {sortedHospitals.map((hospital) => (
          <li key={hospital.id} className="Distance-hospital-item">
            <span className="Distance-hospital-name">{hospital.name}</span>
            <div className="Distance-congestion-info">
              <span>성인: {hospital.Acongestion}</span>
            </div>
            <div className="Distance-congestion-info">
              <span>소아: {hospital.Bcongestion}</span>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default DistanceOrder;