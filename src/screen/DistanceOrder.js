import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import "../css/DistanceOrder.css";

const sampleHospitals = [
  {
    id: 1,
    name: "Hospital 1",
    distance: "3.1",
    Acongestion: "2",
    Bcongestion: "2",
  },
  {
    id: 2,
    name: "Hospital 2",
    distance: "2.3",
    Acongestion: "1",
    Bcongestion: "3",
  },
  {
    id: 3,
    name: "Hospital 3",
    distance: "1.7",
    Acongestion: "4",
    Bcongestion: "6",
  },
  {
    id: 4,
    name: "Hospital 4",
    distance: "5.4",
    Acongestion: "3",
    Bcongestion: "4",
  },
  {
    id: 5,
    name: "Hospital 5",
    distance: "4.2",
    Acongestion: "4",
    Bcongestion: "7",
  },
  {
    id: 6,
    name: "Hospital 6",
    distance: "3.2",
    Acongestion: "5",
    Bcongestion: "3",
  },
  {
    id: 7,
    name: "Hospital 7",
    distance: "5.7",
    Acongestion: "6",
    Bcongestion: "2",
  },
  {
    id: 8,
    name: "Hospital 8",
    distance: "2.3",
    Acongestion: "2",
    Bcongestion: "4",
  },
  {
    id: 9,
    name: "Hospital 9",
    distance: "6.7",
    Acongestion: "7",
    Bcongestion: "7",
  },
  {
    id: 10,
    name: "Hospital 10",
    distance: "8.2",
    Acongestion: "8",
    Bcongestion: "3",
  },
  {
    id: 11,
    name: "Hospital 11",
    distance: "9.1",
    Acongestion: "2",
    Bcongestion: "4",
  },
  {
    id: 12,
    name: "Hospital 12",
    distance: "0.5",
    Acongestion: "1",
    Bcongestion: "3",
  },
  // ... 나머지 병원 데이터
];

const DistanceOrder = () => {
    const location = useLocation();
    const userLocation = location.state ? location.state.userLocation : "Default Location"; 
    const [sortedHospitals, setSortedHospitals] = useState([]);
  
    useEffect(() => {
      const sorted = [...sampleHospitals].sort(
        (a, b) => parseFloat(a.distance) - parseFloat(b.distance)
      );
      setSortedHospitals(sorted);
    }, []);
  
    return (
      <div className="distance-container">
        <h2>{userLocation}</h2>
  
        <ul className="hospital-list">
          {sortedHospitals.map((hospital) => (
            <li key={hospital.id} className="hospital-item">
              <span className="hospital-name">{hospital.name}</span>
              <span className="hospital-distance">{hospital.distance} km</span>
              <div className="congestion-info">
                <span style={{ display: "block" }}>
                  성인 혼잡도: {hospital.Acongestion}
                </span>
                <span style={{ display: "block" }}>
                  소아 혼잡도: {hospital.Bcongestion}
                </span>
              </div>
            </li>
          ))}
        </ul>
      </div>
    );
  };
  
  export default DistanceOrder;