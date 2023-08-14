import React, { useState } from "react";
import "../css/CongestionLevelOrder.css"; // CSS 파일 import

const sampleData = [
  { id: 1, name: "Location A", Acongestion: "10", Bcongestion: "10" },
  { id: 2, name: "Location B", Acongestion: "9", Bcongestion: "6" },
  { id: 3, name: "Location C", Acongestion: "8", Bcongestion: "7" },
  { id: 4, name: "Location D", Acongestion: "6", Bcongestion: "1" },
  { id: 5, name: "Location E", Acongestion: "5", Bcongestion: "4" },
  { id: 6, name: "Location F", Acongestion: "2", Bcongestion: "5" },
];

const CongestionLevelOrder = () => {
  const [sortedData, setSortedData] = useState([...sampleData]);
  const [activeButton, setActiveButton] = useState(""); // 활성화된 버튼을 추적하기 위한 state 추가

  const handleSort = (type) => {
    const sorted = [...sampleData].sort((a, b) => {
      if (type === "adult") {
        setActiveButton("adult");
        return parseInt(a.Acongestion) - parseInt(b.Acongestion);
      } else {
        setActiveButton("child");
        return parseInt(a.Bcongestion) - parseInt(b.Bcongestion);
      }
    });
    setSortedData(sorted);
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
          <li key={item.id} className="CongestionLevelOrder-item">
            <span className="CongestionLevelOrder-name">{item.name}</span>
            <span className="CongestionLevelOrder-congestion-info">
              성인: {item.Acongestion}
            </span>
            <span className="CongestionLevelOrder-congestion-info">
              소아: {item.Bcongestion}
            </span>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CongestionLevelOrder;
