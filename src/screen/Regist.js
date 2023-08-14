import React, { useState } from "react";
import "../css/Regist.css";

// Mock function to simulate API call for registration
const mockRegisterAPI = (name, email, password, confirmPassword) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (password === confirmPassword) {
        resolve({ success: true });
      } else {
        reject({ success: false, message: "Passwords do not match" });
      }
    }, 1000);
  });
};

const Regist = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");

  const handleRegister = async () => {
    try {
      const response = await mockRegisterAPI(
        name,
        email,
        password,
        confirmPassword
      );
      if (response.success) {
        // Redirect to login or display success message
        console.log("Registration successful!");
      }
    } catch (err) {
      setError(err.message);
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
