import { useState } from 'react';
import { useNavigate, Link, Routes, Route } from 'react-router-dom';
import { Button } from '@mui/material';
import { styled } from '@mui/styles';
import LoginForm from "../components/LoginForm";
import Dashboard from "./Dashboard";
import Register from './Register';
import '../style/pages/Home.css';

const CustomButton = styled(Button)({
  backgroundColor: '#D14D72',
  color: 'white',
  '&:hover': {
    backgroundColor: '#BE5A83',
  },
});

const Home = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [loginInfo, setLoginInfo] = useState(null);
  const navigate = useNavigate();

  const handleLoginSuccess = (logindata) => {
    setIsLoggedIn(true);
    setLoginInfo(logindata);
    // console.log(logindata)
    navigate('/dashboard');
  }

  return (
    <Routes>
      <Route path="/" element={!isLoggedIn ? (
        <>
          <LoginForm onLoginSuccess={handleLoginSuccess} />
          <div className='register-box'>
            <CustomButton component={Link} to="/register" variant="contained">&nbsp;관리자 등록&nbsp;</CustomButton>
          </div>
        </>
      ) : null} />
      <Route path="/dashboard" element={isLoggedIn ? <Dashboard loginInfo={loginInfo} /> : null} />
      <Route path="/register" element={!isLoggedIn ? <Register /> : null} />
    </Routes>
  );
}

export default Home;