import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import {useAuth} from "./AuthContext";
import "../css/Login.css";
import axios from "axios";
import { parseJwt } from "../utils/JwtUtils";

const ManagerLogin = () => {
  const [mnid, setmnid] = useState("");
  const [mnpw, setmnpw] = useState("");
  const [error, setError] = useState("");
  const [LoggedIn, setIsLoggedIn] = useState(false);
  const Navigate = useNavigate();
  const Auth = useAuth();
  // Todo: 백 API로 변경하기
  const BackAPI = "http://localhost:8080/manager/signIn";

  const handleLogin = async () => {
      const response = await axios.post(BackAPI, {
        mnid: mnid,
        mnpw: mnpw,
      }).then((response) => {
          console.log(response)
          const {accessToken} = response.data;
          const data = parseJwt(accessToken);

          const user = {data, accessToken};
          Auth.userLogin(user);
          setIsLoggedIn(user);

          if(data.role === 'USER') {
            alert("Log in with your administrator account");
            Navigate('/Main');
          }

          console.log("Manager Logged in successfully!");
          Navigate("/AppManager");
        }).catch((error) => {
          alert(error.response.data);
        });
  }

  return (
    <div className="login-container">
      <h2>Manager Login</h2>
      <input
        type="ID"
        placeholder="Id"
        value={mnid}
        onChange={(e) => setmnid(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        value={mnpw}
        onChange={(e) => setmnpw(e.target.value)}
      />
      <button onClick={handleLogin} className="login-button">
        Login
      </button>
      {error && <p>{error}</p>}
    </div>
  );
};

export default ManagerLogin;
