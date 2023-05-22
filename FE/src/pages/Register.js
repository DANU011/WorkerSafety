import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { TextField, Button } from '@mui/material';
import { styled } from '@mui/styles';
import api from '../service/api';
import '../style/pages/Register.css';

const CustomTextField = styled(TextField)({
  '& .MuiInputLabel-root.Mui-focused': {
    color: '#FF7F50',
  },
  '& .MuiOutlinedInput-root': {
    '&.Mui-focused fieldset': {
      borderColor: '#FF7F50',
    },
  },
});

const Register = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await api.post('/register', { username, password });
      navigate('/');
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className='register-container'>
      <h1 className='register-title'>관리자 권한 부여</h1>
      <form className='register-form' onSubmit={handleSubmit}>
        <CustomTextField
          label="사용자명"
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          sx={{ marginTop: '30px' }}
        />
        <CustomTextField
          label="비밀번호"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          sx={{ marginTop: '30px' }}
        />
        <Button type="submit" variant="contained" sx={{ backgroundColor: '#e9531d', marginTop: '30px', color: 'white', '&:hover': { backgroundColor: '#FF7F50' } }}>권한 부여</Button>
      </form>
    </div>
  );
};

export default Register;