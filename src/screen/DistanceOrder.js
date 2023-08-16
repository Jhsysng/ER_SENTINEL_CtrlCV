import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "../css/DistanceOrder.css";
import axios from "axios";

// Todo: 이거 나중에 지우기
const sampleHospitals = [
  {
    id: 1,
    dutyId: "A000001",
    name: "Hospital 1",
    distance: "3.1",
    adultpercent: "2",
    pediatricpercent: "2",
  },
  {
    id: 2,
    dutyId: "A000002",
    name: "Hospital 2",
    distance: "2.3",
    adultpercent: "1",
    pediatricpercent: "3",
  },
  {
    id: 3,
    dutyId: "A000003",
    name: "Hospital 3",
    distance: "1.7",
    adultpercent: "4",
    pediatricpercent: "6",
  },
  {
    id: 4,
    dutyId: "A000004",
    name: "Hospital 4",
    distance: "5.4",
    adultpercent: "3",
    pediatricpercent: "4",
  },
  {
    id: 5,
    dutyId: "A000005",
    name: "Hospital 5",
    distance: "4.2",
    adultpercent: "4",
    pediatricpercent: "7",
  },
  {
    id: 6,
    dutyId: "A000006",
    name: "Hospital 6",
    distance: "3.2",
    adultpercent: "5",
    pediatricpercent: "3",
  },
  {
    id: 7,
    dutyId: "A000007",
    name: "Hospital 7",
    distance: "5.7",
    adultpercent: "6",
    pediatricpercent: "2",
  },
  {
    id: 8,
    dutyId: "A000008",
    name: "Hospital 8",
    distance: "2.3",
    adultpercent: "2",
    pediatricpercent: "4",
  },
  {
    id: 9,
    dutyId: "A000009",
    name: "Hospital 9",
    distance: "6.7",
    adultpercent: "7",
    pediatricpercent: "7",
  },
  {
    id: 10,
    dutyId: "A0000010",
    name: "Hospital 10",
    distance: "8.2",
    adultpercent: "8",
    pediatricpercent: "3",
  },
  {
    id: 11,
    dutyId: "A0000011",
    name: "Hospital 11",
    distance: "9.1",
    adultpercent: "2",
    pediatricpercent: "4",
  },
  {
    id: 12,
    dutyId: "A0000012",
    name: "Hospital 12",
    distance: "0.5",
    adultpercent: "1",
    pediatricpercent: "3",
  },
];

const DistanceOrder = () => {
  const [sortedHospitals, setSortedHospitals] = useState([]);
  const [userLocation, setUserLocation] = useState("Fetching location...");
  const [Latitude, setLatitude] = useState("");
  const [Longitude, setLongitude] = useState("");
  const Navigate = useNavigate();
  // Todo: 백 API 연결하기
  const BackAPI = "";

  const handleHospitalClick = (hospitalCode) => {
    Navigate(`/HInfo/${hospitalCode}`);
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
      {userLocation === "User denied the request for Geolocation." && (
        <div>
          <p>Please allow location access to use this feature.</p>
          <button onClick={requestPermissionAgain}>
            Request Permission Again
          </button>
        </div>
      )}
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
