import React, { useContext, useEffect, useState } from "react";
import {Navigate, useParams} from "react-router-dom";
import { Cookies } from "react-cookie";
import { parseJwt } from "../utils/JwtUtils";
import { config } from "../utils/Constants";
import axios from "axios";
import "../css/HInfo.css";
import "../utils/ApiUtils.js";
import AuthContext from "./AuthContext";

const AppHManager = () => {

  useEffect(() => {
    if (Auth.getUser().data.role !== "ADMIN") {
      alert("Only the manager has access!!!!");
      Navigate('/Main');
    }
    getAllUserData();
  }, []);

  const Auth = useContext(AuthContext);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [servereList, setservereList] = useState([]); //
  const [messageList, setmessageList] = useState([]); //
  const [emergencyList, setemergencyList] = useState([]); //
  const [equipmentList, setequipmentList] = useState([]); //
  const [hospitalList, sethospitalList] = useState([]); //

  const getAllUserData = async () => {
    const response5 = await axios.get(`http://localhost:8080/manager/hospital`)
        .then((result) => {
          sethospitalList(result.data.list);
        });
    const response1 = await axios.get(`http://localhost:8080/manager/hospital/{dutyId}/servere`)
        .then((result) => {
          setservereList(result.data.list);
        });
    const response2= await axios.get(`http://localhost:8080/manager/hospital/{dutyId}/message`)
        .then((result) => {
          setmessageList(result.data.list);
        });
    const response3 = await axios.get(`http://localhost:8080/manager/hospital/{dutyId}/emergency`)
        .then((result) => {
          setemergencyList(result.data.list);
        });
    const response4 = await axios.get(`http://localhost:8080/manager/hospital/{dutyId}/equipment`)
        .then((result) => {
          setequipmentList(result.data.list);
        });
  }

  return (
      <div className="HInfo-hospital-details-container">
        <h1 className="HInfo-hospital-name">User Manager</h1>

        <div className="HInfo-hospital-info-section HInfo-procedure-section">
          <div className="HInfo-procedure-header">
            <div className="HInfo-title-container">
              <h4 className="HInfo-procedure-title">실시간 가용장비 정보</h4>
            </div>
          </div>
          <div className="HInfo-congestion-container">
            <div className="HInfo-procedure-content">
              <div className="HInfo-procedure-list-container">
                <ul className="HInfo-procedure-list-left">
                  {hospitalList && hospitalList.map((data, index) => (
                      <li key={index} className="HInfo-procedure-item">
                        {JSON.stringify(data.id)}
                      </li>
                  ))}
                </ul>
                <ul className="HInfo-procedure-list-left">
                  {servereList && servereList.map((data, username) => (
                      <li key={username} className="HInfo-procedure-item">
                        {JSON.stringify(data.username)}
                      </li>
                  ))}
                </ul>
                <ul className="HInfo-procedure-list-left">
                  {messageList && messageList.map((data, index) => (
                      <li key={index} className="HInfo-procedure-item">
                        {JSON.stringify(data.email)}
                      </li>
                  ))}
                </ul>
                <ul className="HInfo-procedure-list-left">
                  {emergencyList && emergencyList.map((data, index) => (
                      <li key={index} className="HInfo-procedure-item">
                        {JSON.stringify(data.role)}
                      </li>
                  ))}
                </ul>
                  <ul className="HInfo-procedure-list-left">
                      {equipmentList && equipmentList.map((data, index) => (
                          <li key={index} className="HInfo-procedure-item">
                              {JSON.stringify(data.role)}
                          </li>
                      ))}
                  </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
  );
};

export default AppHManager;
