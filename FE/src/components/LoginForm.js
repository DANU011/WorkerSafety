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
    api.post('http://localhost:8080/login', { managerid, password }, { withCredentials: true })
      .then(response => {
        const jwtToken = response.data.token;
        sessionStorage.setItem('token', jwtToken);
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