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
  const [managerid, setManagerid] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [role, setRole] = useState('');

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await api.post('/user/join', { managerid, password, name, role });
      navigate('/');
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className='register-container'>
      <h1 className='register-title'>관리자 등록하기</h1>
      <form className='register-form' onSubmit={handleSubmit}>
        <CustomTextField
          label="관리자ID"
          type="text"
          value={managerid}
          onChange={(e) => setManagerid(e.target.value)}
          required
        />
        <CustomTextField
          label="비밀번호"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          sx={{ marginTop: '20px' }}
        />
        <CustomTextField
          label="관리자명"
          type="name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
          sx={{ marginTop: '20px' }}
        />
        <Button type="submit" variant="contained" size="small" sx={{ backgroundColor: '#e9531d', marginTop: '30px', color: 'white', '&:hover': { backgroundColor: '#FF7F50' } }}>관리자 등록</Button>
      </form>
    </div>
  );
};

export default Register;