import React, { useState, useEffect } from "react";
import "../css/CongestionLevelOrder.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const CongestionLevelOrder = () => {
  const [sortedData, setSortedData] = useState([...[]]);
  const [activeButton, setActiveButton] = useState("");
  const [FirstAddress, setFirstAddress] = useState("");
  const [SecondAddress, setSecondAddress] = useState("");
  const Navigate = useNavigate();
  // Todo: 백이랑 API 연결하기
  const BackAPI = "";

  useEffect(() => {
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
                  Authorization: "KakaoAK e66ae55f0df11d36b2b954ae39f001eb",
                  "Content-Type":
                    "application/x-www-form-urlencoded;charset=utf-8",
                },
              }
            );
            const data = await response.json();
            if (data.documents && data.documents.length > 0) {
              const address = data.documents[0].address;
              setFirstAddress(address.region_1depth_name); // 시
              console.log(FirstAddress);
              setSecondAddress(address.region_2depth_name); // 구
              console.log(SecondAddress);
            }
          } catch (error) {
            console.error("Error fetching address", error);
          }
        },
        (error) => {
          console.error("Error getting geolocation", error);
        }
      );
    }
  }, []);

  // Todo: 아마 이거 혼잡도 개념 바뀔거임 정렬 sampleData 나중에 고치기
  const handleSort = async (type) => {
    let data;
    try {
      const response = await axios.get(`http://localhost:8080/congestion/congestionlist`, {
        params: {
          firstAddress: FirstAddress,
          secondAddress: SecondAddress,
          isadult: type === "adult",
        }
      });

      if (response.data) {
        // 백엔드에서 받은 병원 데이터로 상태 업데이트
        setSortedData(response.data.data);
        data = response.data.data;
      }
      console.log(response.data.data);
    } catch (error) {
      console.error("Error fetching sorted hospitals", error);
    }

    if (type === "adult") {
      setActiveButton("adult");
      const sorted = data.sort(
          (a, b) => parseInt(a.adultpercent) - parseInt(b.adultpercent)
      );
      setSortedData(sorted);
    } else {
      setActiveButton("child");
      const sorted = data.sort(
          (a, b) => parseInt(a.pediatricpercent) - parseInt(b.pediatricpercent)
      );
      setSortedData(sorted);
      console.log(sorted);
    }
  };

  const handleItemClick = (dutyId) => {
    Navigate(`/HInfo`, {state: {dutyId: dutyId}});
  };

  return (
    <div className="CongestionLevelOrder-container">
      <h2 className="CongestionLevelOrder-h2">혼잡도 기준</h2>
      <div className="CongestionLevelOrder-button-group">
        <button
          onClick={() => handleSort("adult")}
          className={activeButton === "adult" ? "active" : ""}
        >
          성인 혼잡도
        </button>
        <button
          onClick={() => handleSort("child")}
          className={activeButton === "child" ? "active" : ""}
        >
          소아 혼잡도
        </button>
      </div>

      <ul className="CongestionLevelOrder-list">
        {sortedData.map((item) => (
          <li
            key={JSON.stringify(item.name)}
            className="CongestionLevelOrder-item"
            onClick={() => handleItemClick(item.dutyId)}
          >
            <span className="CongestionLevelOrder-name">{item.name}</span>
            <span className="CongestionLevelOrder-congestion-info">
              성인: {JSON.stringify(item.adultpercent)}
            </span>
            <span className="CongestionLevelOrder-congestion-info">
              소아: {JSON.stringify(item.pediatricpercent)}
            </span>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CongestionLevelOrder;

// Todo : api 연동, 미션, css 