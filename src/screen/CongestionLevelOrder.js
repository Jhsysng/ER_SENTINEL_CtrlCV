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

  const handleSort = (type) => {
    const sorted = [...sampleData].sort((a, b) => {
      if (type === "adult") {
        return parseInt(a.Acongestion) - parseInt(b.Acongestion);
      } else {
        return parseInt(a.Bcongestion) - parseInt(b.Bcongestion);
      }
    });
    setSortedData(sorted);
  };

  return (
    <div className="congestion-container">
      <div className="button-group">
        <button onClick={() => handleSort("adult")}>성인</button>
        <button onClick={() => handleSort("child")}>소아</button>
      </div>

      <ul className="list">
        {sortedData.map((item) => (
          <li key={item.id}>
            {item.name} - 성인: {item.Acongestion} / 소아: {item.Bcongestion}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CongestionLevelOrder;
