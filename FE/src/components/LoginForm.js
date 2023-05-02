import { useState } from 'react';
import axios from 'axios';
import "../style/LoginForm.css";

const LoginForm = ({onLoginSuccess}) => {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (event) => {
    // 스프링부트의 API와 연결 - 경로 설정 확인하기
    event.preventDefault();
    axios.post('http://localhost:8080/login', {id, password})
    .then(response => {
      onLoginSuccess();
    })
    .catch(error => {
      console.error(error);
    });
  }

  return (
    <div className="login-form-container">
      <h1 className="login-form-title">Log in to your account</h1>
      <form className="login-form" onSubmit={handleLogin}>
        <div className="login-form-field">
          <label htmlFor="id">ID</label>
          <input
            type="text"
            id="id"
            value={id}
            onChange={(e) => setId(e.target.value)}
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