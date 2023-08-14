import React from "react";
import { Link } from "react-router-dom";
import "../css/Main.css";
import CongestionLevelOrder from "../components/CongestionLevelOrder.png";
import maporder from "../components/maporder.png";
import Distanceorder from "../components/Distanceorder.png";

const Main = () => {
  return (
    <div className="main-container">
      <Link
        to={{
          pathname: "/DistanceOrder",
        }}
      >
        <button className="main-button">
          <img src={Distanceorder} alt="거리순 아이콘" /> 
        </button>
      </Link>
      <Link to="/CongestionLevelOrder">
        <button className="main-button">
          <img src={CongestionLevelOrder} alt="혼잡도 아이콘" /> 
        </button>
      </Link>
      <Link to="/MapOrder">
        <button className="main-button">
          <img src={maporder} alt="지도 아이콘" /> 
        </button>
      </Link>
      <Link
        to={{
          pathname: "/HInfo"
        }}
      >
        <button className="main-button">
          <img src="path_to_your_png_icon" alt="병원 정보 아이콘" />
        </button>
      </Link>
    </div>
  );
};

export default Main;
