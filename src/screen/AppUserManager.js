import React, { useContext, useEffect, useState } from "react";
import {Navigate, useParams} from "react-router-dom";
import { Cookies } from "react-cookie";
import { parseJwt } from "../utils/JwtUtils";
import { config } from "../utils/Constants";
import axios from "axios";
import "../css/HInfo.css";
import "../utils/ApiUtils.js";
import AuthContext from "./AuthContext";

const AppUserManager = () => {
  // AuthContext : 전역적인 변수.
  // Auth.getUser().accessToken : 토큰을 가져오는 것
  // header에 Authorization: Bearer {accessToken} 이거 넣어주기.

    useEffect(() => {
      if (Auth.getUser().data.role !== "ADMIN") {
          alert("Only the manager has access!!!!");
          Navigate('/Main');
      }
      getAllUserData();
    }, []);

  const Auth = useContext(AuthContext);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userList, setUserList] = useState([]); //


  const getAllUserData = async () => {
    const response = await axios.get(`http://localhost:8080/manager/user`)
        .then((result) => {
          setUserList(result.data.list);
        });
  }

  const DeleteData = async () => {
    try {
      const response = await axios.post(`http://localhost:8080/manager/user`, {
        username: "manager"
      });
      setUserList(response.data);
    } catch (error) {
      console.error("Failed to delete hospital data", error);
    }
  };

  return (
    <div className="HInfo-hospital-details-container">
      <h1 className="HInfo-hospital-name">User Manager</h1>

      <div className="HInfo-hospital-info-section HInfo-procedure-section">
        <div className="HInfo-procedure-header">
          <div className="HInfo-title-container">
            <h4 className="HInfo-procedure-title">User manager</h4>
          </div>
        </div>
        <div className="HInfo-congestion-container">
          <div className="HInfo-procedure-content">
            <div className="HInfo-procedure-list-container">
              <ul className="HInfo-procedure-list-left">
                {userList && userList.map((data, index) => (
                    <li key={index} className="HInfo-procedure-item">
                      {JSON.stringify(data.id)}
                    </li>
                ))}
              </ul>
              <ul className="HInfo-procedure-list-left">
                {userList && userList.map((data, username) => (
                    <li key={username} className="HInfo-procedure-item">
                      {JSON.stringify(data.username)}
                    </li>
                ))}
              </ul>
              <ul className="HInfo-procedure-list-left">
                {userList && userList.map((data, index) => (
                    <li key={index} className="HInfo-procedure-item">
                      {JSON.stringify(data.email)}
                    </li>
                ))}
              </ul>
              <ul className="HInfo-procedure-list-left">
                {userList && userList.map((data, index) => (
                    <li key={index} className="HInfo-procedure-item">
                      {JSON.stringify(data.role)}
                    </li>
                ))}
              </ul>
              <div className="deletebutton" onClick={DeleteData}>
                <button>삭제하기</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AppUserManager;
