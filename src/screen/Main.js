import React from "react";
import { Link } from "react-router-dom";
import "../css/Main.css";

const Main = () => {
  return (
    <div className="main-container">
      <Link
        to={{
          pathname: "/DistanceOrder",
          state: { userLocation: "서울특별시 강남구" },
        }}
      >
        <button className="main-button">거리순 기준 선택</button>
      </Link>
      <Link to="/CongestionLevelOrder">
        <button className="main-button">혼잡도 기준 선택</button>
      </Link>
      <Link to="/MapOrder">
        <button className="main-button">지도 기준 선택</button>
      </Link>
    </div>
  );
};

export default Main;
