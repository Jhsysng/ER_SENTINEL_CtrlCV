import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import "../css/Login.css";
import axios from "axios";

const ManagerLogin = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const Navigate = useNavigate();
  // Todo: 백 API로 변경하기
  const BackAPI = "";

  const handleLogin = async () => {
    try {
      const response = await axios.post(BackAPI, {
        email: email,
        password: password,
      });

      if (response.data.success) {
        console.log("Manager Logged in successfully!");
        Navigate.push("/AppManager");
      } else {
        setError(response.data.message || "Manager Login failed");
      }
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="login-container">
      <h2>Manager Login</h2>
      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={handleLogin} className="login-button">
        Login
      </button>
      {error && <p>{error}</p>}
    </div>
  );
};

export default ManagerLogin;
