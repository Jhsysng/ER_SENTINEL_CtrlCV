import React, { useState, useEffect } from "react";
import "../css/CongestionLevelOrder.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

// Todo: 이거 나중에 지우기
const sampleData = [
  {
    id: 1,
    name: "Location A",
    adultpercent: "10",
    pediatricpercent: "10",
    dutyId: "A000001",
  },
  {
    id: 2,
    name: "Location B",
    adultpercent: "9",
    pediatricpercent: "6",
    dutyId: "A000002",
  },
  {
    id: 3,
    name: "Location C",
    adultpercent: "8",
    pediatricpercent: "7",
    dutyId: "A000003",
  },
  {
    id: 4,
    name: "Location D",
    adultpercent: "6",
    pediatricpercent: "1",
    dutyId: "A000004",
  },
  {
    id: 5,
    name: "Location E",
    adultpercent: "5",
    pediatricpercent: "4",
    dutyId: "A000005",
  },
  {
    id: 6,
    name: "Location F",
    adultpercent: "2",
    pediatricpercent: "5",
    dutyId: "A000006",
  },
  {
    id: 7,
    name: "Location D",
    adultpercent: "12",
    pediatricpercent: "11",
    dutyId: "A000007",
  },
  {
    id: 8,
    name: "Location E",
    adultpercent: "53",
    pediatricpercent: "41",
    dutyId: "A000008",
  },
  {
    id: 9,
    name: "Location F",
    adultpercent: "21",
    pediatricpercent: "51",
    dutyId: "A000009",
  },
  {
    id: 10,
    name: "Location D",
    adultpercent: "63",
    pediatricpercent: "12",
    dutyId: "A000010",
  },
  {
    id: 11,
    name: "Location E",
    adultpercent: "54",
    pediatricpercent: "43",
    dutyId: "A000011",
  },
  {
    id: 12,
    name: "Location F",
    adultpercent: "25",
    pediatricpercent: "53",
    dutyId: "A000012",
  },
];

const CongestionLevelOrder = () => {
  const [sortedData, setSortedData] = useState([...sampleData]);
  const [activeButton, setActiveButton] = useState("");
  const [firstAddress, setFirstAddress] = useState("");
  const [secondAddress, setSecondAddress] = useState("");
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
              setSecondAddress(address.region_2depth_name); // 구
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
    if (type === "adult") {
      setActiveButton("adult");
      const sorted = [...sampleData].sort(
        (a, b) => parseInt(a.adultpercent) - parseInt(b.adultpercent)
      );
      setSortedData(sorted);
    } else {
      setActiveButton("child");
      const sorted = [...sampleData].sort(
        (a, b) => parseInt(a.pediatricpercent) - parseInt(b.pediatricpercent)
      );
      setSortedData(sorted);
    }

    /* Todo: 백 연결하고 이거 주석 처리 풀기
    try {
      const response = await axios.get(/api/BackAPI, {
        params: {
          Firstaddress: firstAddress,
          secondaddress: secondAddress,
          isadult: type === "adult",
        }
      });

      if (response.data) {
        // 백엔드에서 받은 병원 데이터로 상태 업데이트
        setSortedData(response.data);
      }
    } catch (error) {
      console.error("Error fetching sorted hospitals", error);
    }
    */
  };

  const handleItemClick = (dutyId) => {
    Navigate(`/HInfo/${dutyId}`);
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
          // Todo: 이거 나중에 id 바꾸기
          <li
            key={item.id}
            className="CongestionLevelOrder-item"
            onClick={() => handleItemClick(item.dutyId)}
          >
            <span className="CongestionLevelOrder-name">{item.name}</span>
            <span className="CongestionLevelOrder-congestion-info">
              성인: {item.adultpercent}
            </span>
            <span className="CongestionLevelOrder-congestion-info">
              소아: {item.pediatricpercent}
            </span>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CongestionLevelOrder;

// Todo : api 연동, 미션, css 