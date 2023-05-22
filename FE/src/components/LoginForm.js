import { useState } from 'react';
import api from '../service/api';
import "../style/components/LoginForm.css";

const LoginForm = ({ onLoginSuccess }) => {
  const [id, setId] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (event) => {
    event.preventDefault();
    if (!id || !password) {
      console.log("아이디와 비밀번호를 입력해주세요.");
      return;
    }
    api.post('/login', { id, password })
      .then(response => {
        onLoginSuccess(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  };

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