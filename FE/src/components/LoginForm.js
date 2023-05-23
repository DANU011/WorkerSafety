import { useState } from 'react';
import api from '../service/api';
import "../style/components/LoginForm.css";

const LoginForm = ({ onLoginSuccess }) => {
  const [managerid, setManageid] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (event) => {
    event.preventDefault();
    if (!managerid || !password) {
      console.log("아이디와 비밀번호를 입력해주세요.");
      return;
    }
    api.post('/login', { managerid, password })
      .then(response => {
        const jwtToken = response.data.token; // jwt 토큰을 받아옴
        // jwt 토큰을 세션 스토리지에 저장
        sessionStorage.setItem('token', jwtToken);
        onLoginSuccess(); // 로그인 성공 처리
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
          <label htmlFor="managerid">ID</label>
          <input
            type="text"
            id="managerid"
            value={managerid}
            onChange={(e) => setManageid(e.target.value)}
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