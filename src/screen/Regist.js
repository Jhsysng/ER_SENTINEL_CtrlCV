import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import "../css/Regist.css";

const Regist = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const Navigate = useNavigate(); 

  // Todo: 백 API로 변경하기
  const BackAPI = "";

  const handleRegister = async () => {
    if (password !== confirmPassword) {
      setError("Passwords do not match");
      return;
    }

    try {
      const response = await axios.post(BackAPI, {
        name: name,
        email: email,
        password: password,
      });

      if (response.data.success) {
        console.log("Registration successful!");
        Navigate.push("/"); 
      } else {
        setError(response.data.message || "Registration failed");
      }
    } catch (err) {
      setError(err.message || "An error occurred");
    }
  };

  return (
    <div className="regist-container">
      <h2>Regist</h2>
      <input
        type="text"
        placeholder="Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
        className="regist-input"
      />
      <input
        type="email"
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        className="regist-input"
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        className="regist-input"
      />
      <input
        type="password"
        placeholder="Confirm Password"
        value={confirmPassword}
        onChange={(e) => setConfirmPassword(e.target.value)}
        className="regist-input"
      />
      <button onClick={handleRegister} className="regist-button">
        Register
      </button>
      {error && <p className="regist-error">{error}</p>}
    </div>
  );
};

export default Regist;