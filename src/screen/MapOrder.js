/* global kakao */
import React, { useEffect, useState } from "react";
import greenSign from "../components/greensign.png";
import orangeSign from "../components/orangesign.png";
import redSign from "../components/redsign.png";
import yellowSign from "../components/yellowsign.png";
import foursign from "../components/foursign.png";
import usericon from "../components/usericon.png";
import "../css/MapOrder.css";

const sampleHospitals = [
  {
    id: 1,
    name: "Hospital 1",
    lat: 33.450701,
    lng: 126.570667,
    Acongestion: 2,
    Pcongestion: 4,
  },
  {
    id: 2,
    name: "Hospital 2",
    lat: 33.451701,
    lng: 126.571667,
    Acongestion: 5,
    Pcongestion: 6,
  },
  {
    id: 3,
    name: "Hospital 3",
    lat: 33.452701,
    lng: 126.572667,
    Acongestion: 4,
    Pcongestion: 2,
  },
  {
    id: 4,
    name: "Hospital 4",
    lat: 33.455701,
    lng: 126.575667,
    Acongestion: 15,
    Pcongestion: 10,
  },
  {
    id: 5,
    name: "Hospital 5",
    lat: 33.451201,
    lng: 126.568867,
    Acongestion: 20,
    Pcongestion: 5,
  },
  {
    id: 6,
    name: "Hospital 6",
    lat: 33.448701,
    lng: 126.573267,
    Acongestion: 5,
    Pcongestion: 12,
  },
  {
    id: 7,
    name: "Hospital 7",
    lat: 33.453701,
    lng: 126.572267,
    Acongestion: 8,
    Pcongestion: 8,
  },
  {
    id: 8,
    name: "Hospital 8",
    lat: 33.452701,
    lng: 126.574867,
    Acongestion: 2,
    Pcongestion: 15,
  },
  {
    id: 9,
    name: "Hospital 9",
    lat: 33.449701,
    lng: 126.569867,
    Acongestion: 22,
    Pcongestion: 14,
  },
  {
    id: 10,
    name: "Hospital 10",
    lat: 33.454701,
    lng: 126.568267,
    Acongestion: 12,
    Pcongestion: 4,
  },
  {
    id: 11,
    name: "Hospital 11",
    lat: 33.456701,
    lng: 126.576667,
    Acongestion: 3,
    Pcongestion: 10,
  },
  {
    id: 12,
    name: "Hospital 12",
    lat: 33.447701,
    lng: 126.571867,
    Acongestion: 10,
    Pcongestion: 20,
  },
  {
    id: 13,
    name: "Hospital 13",
    lat: 33.458701,
    lng: 126.573867,
    Acongestion: 18,
    Pcongestion: 6,
  },
  {
    id: 14,
    name: "Hospital 14",
    lat: 33.456701,
    lng: 126.578867,
    Acongestion: 21,
    Pcongestion: 8,
  },
  {
    id: 15,
    name: "Hospital 15",
    lat: 33.457701,
    lng: 126.572867,
    Acongestion: 7,
    Pcongestion: 9,
  },
  {
    id: 16,
    name: "Hospital 16",
    lat: 33.451701,
    lng: 126.577867,
    Acongestion: 9,
    Pcongestion: 16,
  },
  {
    id: 17,
    name: "Hospital 17",
    lat: 33.450701,
    lng: 126.574867,
    Acongestion: 19,
    Pcongestion: 18,
  },
  {
    id: 18,
    name: "Hospital 18",
    lat: 33.459701,
    lng: 126.575867,
    Acongestion: 6,
    Pcongestion: 12,
  },
  {
    id: 19,
    name: "Hospital 19",
    lat: 33.455701,
    lng: 126.579867,
    Acongestion: 14,
    Pcongestion: 22,
  },
  {
    id: 20,
    name: "Hospital 20",
    lat: 33.457701,
    lng: 126.576867,
    Acongestion: 23,
    Pcongestion: 3,
  },
  {
    id: 21,
    name: "Hospital 21",
    lat: 33.453701,
    lng: 126.577867,
    Acongestion: 13,
    Pcongestion: 7,
  },
  {
    id: 22,
    name: "Hospital 22",
    lat: 33.452701,
    lng: 126.570867,
    Acongestion: 11,
    Pcongestion: 10,
  },
  {
    id: 23,
    name: "Hospital 23",
    lat: 33.458701,
    lng: 126.572867,
    Acongestion: 4,
    Pcongestion: 19,
  },
];

const MapOrder = () => {
  const [congestionType, setCongestionType] = useState("Acongestion");

  useEffect(() => {
    var mapContainer = document.getElementById("map");

    var mapOption = {
      center: new kakao.maps.LatLng(33.450701, 126.570667),
      level: 7,
    };

    var map = new kakao.maps.Map(mapContainer, mapOption);

    kakao.maps.event.addListener(map, "zoom_changed", function () {
      const zoomLevel = map.getLevel();
      const size = new kakao.maps.Size(40 - zoomLevel * 2, 40 - zoomLevel * 2);
    });

    const displayHospitalsOnMap = (hospitals) => {
      hospitals.forEach((hospital) => {
        const position = new kakao.maps.LatLng(hospital.lat, hospital.lng);
        let iconSrc;
        const congestionValue = hospital[congestionType];
        if (congestionValue >= 20) {
          iconSrc = redSign;
        } else if (congestionValue >= 10) {
          iconSrc = orangeSign;
        } else if (congestionValue >= 5) {
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
          content: content
        });
    
        // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
        kakao.maps.event.addListener(marker, 'click', function() {
          customOverlay.setMap(map);
        });
    
        // 지도를 클릭했을 때 커스텀 오버레이를 숨깁니다
        kakao.maps.event.addListener(map, 'click', function() {
          customOverlay.setMap(null);
        });
      });
    };

    displayHospitalsOnMap(sampleHospitals);

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        function (position) {
          const lat = position.coords.latitude;
          const lng = position.coords.longitude;

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
        function (error) {
          console.log("Error occurred. Error code: " + error.code);
        }
      );
    } else {
      console.log("Geolocation is not supported by this browser.");
    }
  }, [congestionType]);

  return (
    <div style={{ padding: "0 20px" }}>
      <div className="mapContainer">
        <button
          onClick={() => setCongestionType("Acongestion")}
          className={`button ${
            congestionType === "Acongestion"
              ? "buttonAcongestion"
              : "buttonDefault"
          }`}
        >
          성인 혼잡도
        </button>
        <button
          onClick={() => setCongestionType("Pcongestion")}
          className={`button ${
            congestionType === "Pcongestion"
              ? "buttonAcongestion"
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
