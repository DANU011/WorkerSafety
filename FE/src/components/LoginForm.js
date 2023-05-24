import { useState } from 'react';
import jwt_decode from 'jwt-decode';
import api from '../service/api';
import "../style/components/LoginForm.css";

const LoginForm = ({ onLoginSuccess }) => {
  const [managerid, setManagerid] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (event) => {
    event.preventDefault();
    if (!managerid || !password) {
      console.log("아이디와 비밀번호를 입력해주세요.");
      return;
    }
    api.post(`/login`, { managerid, password }, { withCredentials: true })
      .then(response => {
        // console.log(response.headers.authorization);
        const jwtToken = response.headers.authorization.split(" ")[1];
        sessionStorage.setItem('token', jwtToken);
        // console.log(sessionStorage.getItem("token"));
        const decodedToken = jwt_decode(jwtToken);
        // const managerid = decodedToken.managerid;
        const managername = decodedToken.managername;
        // console.log(managerid, managername);
        
        const loginInfo = {
          name: managername,
        };

        onLoginSuccess(loginInfo);
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
            onChange={(e) => setManagerid(e.target.value)}
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