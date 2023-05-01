import React from 'react';
import { useState } from "react";
import "../style/LoginForm.css";

function LoginForm() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = () => {
    // TODO: 스프링부트와 연결하여 로그인 처리하기
  };

  return (
    <div className="login-form-container">
      <h1 className="login-form-title">Log in to your account</h1>
      <form className="login-form">
        <div className="login-form-field">
          <label htmlFor="email">ID</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <div className="login-form-field">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button className="login-form-button" onClick={handleLogin}>
          LOG IN
        </button>
      </form>
    </div>
  );
}

export default LoginForm;