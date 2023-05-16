import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginForm from "../components/LoginForm";
import Dashboard from "./Dashboard";

const Home = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [loginInfo, setLoginInfo] = useState(null);
  const navigate = useNavigate();

  const handleLoginSuccess = (logindata) => {
    setIsLoggedIn(true);
    setLoginInfo(logindata);
    navigate('/dashboard');
  }

  return (
    <>
      {!isLoggedIn && <LoginForm onLoginSuccess={handleLoginSuccess} />}
      {isLoggedIn && <Dashboard loginInfo={loginInfo} />}
    </>
  );
}

export default Home;