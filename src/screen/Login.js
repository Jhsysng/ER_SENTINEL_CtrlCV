import React, { useState } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "../css/Login.css";
import axios from "axios"; 

const Login = () => {
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
      <button onClick={handleLogin}>Login</button>
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
