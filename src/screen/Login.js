import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import "../css/Login.css";
import axios from "axios";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const Navigate = useNavigate();
  const REST_API_KEY = "";
  const REDIRECT_URI = "";
  const Kakaolink = `http://localhost:8080/oauth2/authorization/kakao`;
  const Naverlink = `http://localhost:8080/oauth2/authorization/naver`;
  const Googlelink = `http://localhost:8080/oauth2/authorization/google`;

  // Todo: 백 API로 변경하기
  const BackAPI = "";

  const StyledButton = styled.button`
    padding: 10px 20px;
    font-size: 16px;
    cursor: pointer;
    border: none;
    border-radius: 5px;
    margin: 5px;
  `;

  const NaverButton = styled(StyledButton)`
    background-color: #1ec800;
    color: white;
  `;

  const GoogleButton = styled(StyledButton)`
    background-color: #db4437;
    color: white;
  `;

  const KakaoButton = styled(StyledButton)`
    background-color: #f7e600;
    color: black;
  `;

  const handleLogin = async () => {
    try {
      const response = await axios.post(BackAPI, {
        email: email,
        password: password,
      });

      if (response.data.success) {
        console.log("Logged in successfully!");
        Navigate.push("/Main");
      } else {
        setError(response.data.message || "Login failed");
      }
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
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

      <a href="http://localhost:8080/oauth2/authorization/naver">
        <NaverButton>네이버 로그인</NaverButton>
      </a>
      <a href="http://localhost:8080/oauth2/authorization/google">
        <GoogleButton>구글 로그인</GoogleButton>
      </a>
      <a href="http://localhost:8080/oauth2/authorization/kakao">
        <KakaoButton>카카오 로그인</KakaoButton>
      </a>

      <Link to="/Regist">
        <button>Regist</button>
      </Link>
      <Link to="/Main">
        <button>Main</button>
      </Link>
      {error && <p>{error}</p>}
    </div>
  );
};

export default Login;
