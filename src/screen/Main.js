import React from "react";
import { Link } from "react-router-dom";
import "../css/Main.css";
import CongestionLevelOrderIcon from "../components/CongestionLevelOrder.png";
import MapOrderIcon from "../components/maporder.png";
import DistanceOrderIcon from "../components/Distanceorder.png";

// Todo: main css 좀 더 만지기
// Todo: main에 있는 병원 상세 정보 경로 나중에 연결시키고 여긴 삭제하기

const Main = () => {
  return (
    <div className="main-container">
      <Link to="/DistanceOrder">
        <img
          className="main-button"
          src={DistanceOrderIcon}
          alt="거리순 아이콘"
        />
      </Link>
      <Link to="/CongestionLevelOrder">
        <img
          className="main-button"
          src={CongestionLevelOrderIcon}
          alt="혼잡도 아이콘"
        />
      </Link>
      <Link to="/MapOrder">
        <img className="main-button" src={MapOrderIcon} alt="지도 아이콘" />
      </Link>
    </div>
  );
};

export default Main;
