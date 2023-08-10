import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../css/Login.css'; // 스타일 시트 추가

// Mock function to simulate API call for login
const mockLoginAPI = (email, password) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (email === "test@example.com" && password === "password123") {
        resolve({ success: true });
      } else {
        reject({ success: false, message: "Invalid email or password" });
      }
    }, 1000);
  });
};

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  
  const handleLogin = async () => {
    try {
      const response = await mockLoginAPI(email, password);
      if (response.success) {
        console.log("Logged in successfully!");
        // 여기서 필요한 로직을 추가하십시오.
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
      <Link to="/Regist"><button>Regist</button></Link>
      <Link to="/Main"><button>Main</button></Link> 
      {error && <p>{error}</p>}
    </div>
  );
};

export default Login;
