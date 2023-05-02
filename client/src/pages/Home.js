import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginForm from "../components/LoginForm";
import Dashboard from "./Dashboard";

function Home() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  function handleLoginSuccess() {
    setIsLoggedIn(true);
    navigate('/dashboard');
  }

  return (
    <>
      {!isLoggedIn && <LoginForm onLoginSuccess={handleLoginSuccess} />}
      {isLoggedIn && <Dashboard />}
    </>
  );
}

export default Home;