import React, { useContext, useEffect, useState } from "react";
import {Navigate, useParams} from "react-router-dom";
import { Cookies } from "react-cookie";
import { parseJwt } from "../utils/JwtUtils";
import { config } from "../utils/Constants";
import axios from "axios";
import "../css/HInfo.css";
import "../utils/ApiUtils.js";
import AuthContext from "./AuthContext";

const AppLogManager = () => {
    useEffect(() => {
        if (Auth.getUser().data.role !== "ADMIN") {
            alert("Only the manager has access!!!!");
            Navigate('/Main');
        }
        getAllUserData();
    }, []);

    const Auth = useContext(AuthContext);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [logList, setLogList] = useState([]); //

    const getAllUserData = async () => {
        const response = await axios.get(`http://localhost:8080/manager/log`)
            .then((result) => {
                setLogList(result.data.data);
            });
    }

    return (
        <div className="HInfo-hospital-details-container">
            <h1 className="HInfo-hospital-name">Log Manager</h1>

            <div className="HInfo-hospital-info-section HInfo-procedure-section">
                <div className="HInfo-procedure-header">
                    <div className="HInfo-title-container">
                        <h4 className="HInfo-procedure-title">Log Manager</h4>
                    </div>
                </div>
                <div className="HInfo-congestion-container">
                    <div className="HInfo-procedure-content">
                        <div className="HInfo-procedure-list-container">
                            <ul className="HInfo-procedure-list-left">
                                {logList && logList.map((data, index) => (
                                    <li key={index} className="HInfo-procedure-item">
                                        {JSON.stringify(data.log)}
                                    </li>
                                ))}
                                <button onClick={() => getAllUserData()}>조회</button>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AppLogManager;
