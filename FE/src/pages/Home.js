import { useState } from 'react';
import { useNavigate, Link, Routes, Route } from 'react-router-dom';
import { Button } from '@mui/material';
import { styled } from '@mui/styles';
import LoginForm from "../components/LoginForm";
import Dashboard from "./Dashboard";
import Register from './Register';
import '../style/pages/Home.css';

const CustomButton = styled(Button)({
  backgroundColor: '#e9531d',
  color: 'white',
  '&:hover': {
    backgroundColor: '#FF7F50',
  },
});

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
    <Routes>
      <Route path="/" element={!isLoggedIn ? (
        <>
          <LoginForm onLoginSuccess={handleLoginSuccess} />
          <div className='register-box'>
            <CustomButton component={Link} to="/register" variant="contained">관리자 권한 부여</CustomButton>
          </div>
        </>
      ) : null} />
      <Route path="/dashboard" element={isLoggedIn ? <Dashboard loginInfo={loginInfo} /> : null} />
      <Route path="/register" element={!isLoggedIn ? <Register /> : null} />
    </Routes>
  );
}

export default Home;