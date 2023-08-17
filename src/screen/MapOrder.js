/* global kakao */
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import greenSign from "../components/greensign.png";
import orangeSign from "../components/orangesign.png";
import redSign from "../components/redsign.png";
import yellowSign from "../components/yellowsign.png";
import foursign from "../components/foursign.png";
import usericon from "../components/usericon.png";
import "../css/MapOrder.css";
import axios from "axios";

// Todo: 이거 나중에 지우기
const sampleHospitals = [
  {
    id: 1,
    name: "Hospital 1",
    latitude: 33.450701,
    longitude: 126.570667,
    adultpercent: 2,
    pediatricpercent: 4,
    dutyId: "A1000001",
  },
  {
    id: 2,
    name: "Hospital 2",
    latitude: 33.451701,
    longitude: 126.571667,
    adultpercent: 5,
    pediatricpercent: 6,
    dutyId: "A1000002",
  },
  {
    id: 3,
    name: "Hospital 3",
    latitude: 33.452701,
    longitude: 126.572667,
    adultpercent: 4,
    pediatricpercent: 2,
    dutyId: "A1000003",
  },
  {
    id: 4,
    name: "Hospital 4",
    latitude: 33.455701,
    longitude: 126.575667,
    adultpercent: 15,
    pediatricpercent: 10,
    dutyId: "A1000004",
  },
  {
    id: 5,
    name: "Hospital 5",
    latitude: 33.451201,
    longitude: 126.568867,
    adultpercent: 20,
    pediatricpercent: 5,
    dutyId: "A1000005",
  },
  {
    id: 6,
    name: "Hospital 6",
    latitude: 33.448701,
    longitude: 126.573267,
    adultpercent: 5,
    pediatricpercent: 12,
    dutyId: "A1000006",
  },
  {
    id: 7,
    name: "Hospital 7",
    latitude: 33.453701,
    longitude: 126.572267,
    adultpercent: 8,
    pediatricpercent: 8,
    dutyId: "A1000007",
  },
  {
    id: 8,
    name: "Hospital 8",
    latitude: 33.452701,
    longitude: 126.574867,
    adultpercent: 2,
    pediatricpercent: 15,
    dutyId: "A1000008",
  },
  {
    id: 9,
    name: "Hospital 9",
    latitude: 33.449701,
    longitude: 126.569867,
    adultpercent: 22,
    pediatricpercent: 14,
    dutyId: "A1000009",
  },
  {
    id: 10,
    name: "Hospital 10",
    latitude: 33.454701,
    longitude: 126.568267,
    adultpercent: 12,
    pediatricpercent: 4,
    dutyId: "A1000010",
  },
  {
    id: 11,
    name: "Hospital 11",
    latitude: 33.456701,
    longitude: 126.576667,
    adultpercent: 3,
    pediatricpercent: 10,
    dutyId: "A1000011",
  },
  {
    id: 12,
    name: "Hospital 12",
    latitude: 33.447701,
    longitude: 126.571867,
    adultpercent: 10,
    pediatricpercent: 20,
    dutyId: "A1000012",
  },
  {
    id: 13,
    name: "Hospital 13",
    latitude: 33.458701,
    longitude: 126.573867,
    adultpercent: 18,
    pediatricpercent: 6,
    dutyId: "A1000013",
  },
  {
    id: 14,
    name: "Hospital 14",
    latitude: 33.456701,
    longitude: 126.578867,
    adultpercent: 21,
    pediatricpercent: 8,
    dutyId: "A1000014",
  },
  {
    id: 15,
    name: "Hospital 15",
    latitude: 33.457701,
    longitude: 126.572867,
    adultpercent: 7,
    pediatricpercent: 9,
    dutyId: "A1000015",
  },
  {
    id: 16,
    name: "Hospital 16",
    latitude: 33.451701,
    longitude: 126.577867,
    adultpercent: 9,
    pediatricpercent: 16,
    dutyId: "A1000016",
  },
  {
    id: 17,
    name: "Hospital 17",
    latitude: 33.450701,
    longitude: 126.574867,
    adultpercent: 19,
    pediatricpercent: 18,
    dutyId: "A1000017",
  },
  {
    id: 18,
    name: "Hospital 18",
    latitude: 33.459701,
    longitude: 126.575867,
    adultpercent: 6,
    pediatricpercent: 12,
    dutyId: "A1000018",
  },
  {
    id: 19,
    name: "Hospital 19",
    latitude: 33.455701,
    longitude: 126.579867,
    adultpercent: 14,
    pediatricpercent: 22,
    dutyId: "A1000019",
  },
  {
    id: 20,
    name: "Hospital 20",
    latitude: 33.457701,
    longitude: 126.576867,
    adultpercent: 23,
    pediatricpercent: 3,
    dutyId: "A1000020",
  },
  {
    id: 21,
    name: "Hospital 21",
    latitude: 33.453701,
    longitude: 126.577867,
    adultpercent: 13,
    pediatricpercent: 7,
    dutyId: "A1000021",
  },
  {
    id: 22,
    name: "Hospital 22",
    latitude: 33.452701,
    longitude: 126.570867,
    adultpercent: 11,
    pediatricpercent: 10,
    dutyId: "A1000022",
  },
  {
    id: 23,
    name: "Hospital 23",
    latitude: 33.458701,
    longitude: 126.572867,
    adultpercent: 4,
    pediatricpercent: 19,
    dutyId: "A1000023",
  },
];

const MapOrder = () => {
  const [congestionType, setCongestionType] = useState("adultpercent");
  const [firstAddress, setFirstAddress] = useState("");
  const [secondAddress, setSecondAddress] = useState("");
  const Navigate = useNavigate();
  let currentOverlay = null;
  // Todo: 백 API랑 연결하기
  const BackAPI = "";

  const displayHospitalsOnMap = (hospitals) => {
    var mapContainer = document.getElementById("map");

    var mapOption = {
      center: new kakao.maps.LatLng(37.5665, 126.978),
      level: 10,
    };

    var map = new kakao.maps.Map(mapContainer, mapOption);

    kakao.maps.event.addListener(map, "zoom_changed", function () {
      const zoomLevel = map.getLevel();
      const size = new kakao.maps.Size(40 - zoomLevel * 2, 40 - zoomLevel * 2);
    });

    hospitals.forEach((hospital) => {
      const position = new kakao.maps.LatLng(
          hospital.latitude,
          hospital.longitude
      );
      let iconSrc;
      const congestionValue = hospital[congestionType];
      if (congestionValue >= 150) {
        iconSrc = redSign;
      } else if (congestionValue >= 100) {
        iconSrc = orangeSign;
      } else if (congestionValue >= 50) {
        iconSrc = yellowSign;
      } else if (congestionValue >= 0) {
        iconSrc = greenSign;
      }
      const imageSize = new kakao.maps.Size(40, 40);
      const imageOption = { offset: new kakao.maps.Point(27, 69) };
      const markerImage = new kakao.maps.MarkerImage(
          iconSrc,
          imageSize,
          imageOption
      );
      const marker = new kakao.maps.Marker({
        position: position,
        image: markerImage,
      });
      marker.setMap(map);

      // 커스텀 오버레이에 표시할 내용입니다
      const content = `<div class ="label">
          <span class="left"></span>
          <span class="center">${hospital.name}: ${congestionValue} 혼잡도</span>
          <span class="right"></span>
          </div>`;

      const customOverlay = new kakao.maps.CustomOverlay({
        position: position,
        content: content,
      });

      // 마커를 클릭했을 때 커스텀 오버레이를 표시하거나 병원 페이지로 이동
      kakao.maps.event.addListener(marker, "click", function () {
        if (currentOverlay === customOverlay) {
          // 현재 클릭한 마커와 연관된 오버레이가 이미 열려있는 경우
          Navigate(`/HInfo`, {state: {dutyId: hospital.dutyId}});
        } else {
          if (currentOverlay) {
            currentOverlay.setMap(null); // 이전 오버레이 숨기기
          }
          customOverlay.setMap(map);
          currentOverlay = customOverlay; // 현재 오버레이 업데이트
        }
      });

      // 지도를 클릭했을 때 커스텀 오버레이를 숨깁니다
      kakao.maps.event.addListener(map, "click", function () {
        if (currentOverlay) {
          currentOverlay.setMap(null);
          currentOverlay = null;
        }
      });
    });
  };

  useEffect(() => {
    var mapContainer = document.getElementById("map");

    var mapOption = {
      center: new kakao.maps.LatLng(37.5665, 126.978),
      level: 10,
    };

    var map = new kakao.maps.Map(mapContainer, mapOption);

    kakao.maps.event.addListener(map, "zoom_changed", function () {
      const zoomLevel = map.getLevel();
      const size = new kakao.maps.Size(40 - zoomLevel * 2, 40 - zoomLevel * 2);
    });

    // Todo: 이거 나중에 sample도 빼줘야 됨 -> 실제 리스트로
    displayHospitalsOnMap(sampleHospitals);

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        async (position) => {
          const lat = position.coords.latitude;
          const lng = position.coords.longitude;
          try {
            const response = await fetch(
              `https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${lng}&y=${lat}`,
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
              setFirstAddress(address.region_1depth_name);
              setSecondAddress(address.region_2depth_name);
            }
          } catch (error) {
            console.error("Error fetching address", error);
          }

          const userPosition = new kakao.maps.LatLng(lat, lng);
          const userIconSrc = usericon;
          const userImageSize = new kakao.maps.Size(40, 40);
          const userImageOption = { offset: new kakao.maps.Point(20, 20) };
          const userMarkerImage = new kakao.maps.MarkerImage(
            userIconSrc,
            userImageSize,
            userImageOption
          );
          const userMarker = new kakao.maps.Marker({
            position: userPosition,
            image: userMarkerImage,
          });
          userMarker.setMap(map);
        },
        (error) => {
          console.log("Error occurred. Error code: " + error.code);
        }
      );
    } else {
      console.log("Geolocation is not supported by this browser.");
    }
  }, [congestionType]);

  const handleSort = async (type) => {
    try {
      const response = await axios.get(`http://localhost:8080/congestion/congestionlist`, {
        params: {
          firstAddress: firstAddress,
          secondAddress: secondAddress,
          isadult: type === "adult",
        },
      });

      if (response.data) {
        displayHospitalsOnMap(response.data.data);
      }
    } catch (error) {
      console.error("Error fetching sorted hospitals", error);
    }

  };

  return (
    <div style={{ padding: "0 20px" }}>
      <div className="mapContainer">
        <button
          onClick={() => {
            setCongestionType("pediatricpercent");
            handleSort("adult");
          }}
          className={`button ${
            congestionType === "pediatricpercent"
              ? "buttonadultpercent"
              : "buttonDefault"
          }`}
        >
          성인 혼잡도
        </button>
        <button
          onClick={() => {
            setCongestionType("adultpercent");
            handleSort("child");
          }}
          className={`button ${
            congestionType === "adultpercent"
              ? "buttonadultpercent"
              : "buttonDefault"
          }`}
        >
          소아 혼잡도
        </button>
      </div>
      <div className="map" id="map"></div>
      <div className="imageContainer">
        <img src={foursign} alt="지도 기준" className="mapImage" />
      </div>
    </div>
  );
};

export default MapOrder;
