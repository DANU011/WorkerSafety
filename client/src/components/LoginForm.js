import React, { useState } from 'react';
import axios from 'axios';
import "../style/LoginForm.css";

const LoginForm = ({onLoginSuccess}) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (event) => {
    // 스프링부트의 API와 연결 - 경로 설정 확인하기
    event.preventDefault();
    // axios.post('/api/login', {email, password})
    // .then(response => {
    //   onLoginSuccess();
    // })
    // .catch(error => {
    //   console.error(error);
    // });
    // 테스트용 더미 데이터
    const dummyEmail = "dummy@dummy.com";
    const dummyPassword = "1234";
    if (email === dummyEmail && password === dummyPassword) {
      onLoginSuccess();
    } else {
      console.error("Login failed.");
    }
  }

  return (
    <div className="login-form-container">
      <h1 className="login-form-title">Log in to your account</h1>
      <form className="login-form" onSubmit={handleLogin}>
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
        <button className="login-form-button" type="submit">
          LOG IN
        </button>
      </form>
    </div>
  );
}

export default LoginForm;