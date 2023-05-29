import { useState } from 'react';
import { Button, CssBaseline, TextField, Box, Typography, Container } from '@mui/material';
import { styled } from '@mui/styles';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import jwt_decode from 'jwt-decode';
import api from '../service/api';
import '../style/components/LoginForm.css';

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

const defaultTheme = createTheme();

const LoginForm = ({ onLoginSuccess }) => {
  const [managerid, setManagerid] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = (event) => {
    event.preventDefault();
    if (!managerid || !password) {
      console.log('아이디와 비밀번호를 입력해주세요.');
      return;
    }
    api
      .post(`/login`, { managerid, password }, { withCredentials: true })
      .then((response) => {
        // console.log(response.headers['authorization']);
        // console.log(response.headers['refreshtoken']);
        const accessToken = response.headers['authorization'].split(' ')[1];
        sessionStorage.setItem('accessToken', accessToken);
        // console.log(accessToken);
        const refreshToken = response.headers['refreshtoken']; 
        sessionStorage.setItem('refreshToken', refreshToken);
        // console.log(refreshToken);
        const decodedToken = jwt_decode(accessToken);
        const managerid = decodedToken.managerid;
        const managername = decodedToken.managername;
        // console.log(managerid, managername);

        const loginInfo = {
          name: managername,
        };

        onLoginSuccess(loginInfo);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Container component="main" maxWidth="xs">
        <CssBaseline />
        <div className="login-form-container">
          <Typography component="h1" variant="h5" className="login-form-title">
            Log in to your account
          </Typography>
          <Box component="form" onSubmit={handleLogin} className="login-form" noValidate>
            <CustomTextField
              margin="normal"
              required
              fullWidth
              id="managerid"
              label="ID"
              name="managerid"
              autoComplete="managerid"
              autoFocus
              value={managerid}
              onChange={(e) => setManagerid(e.target.value)}
            />
            <CustomTextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Password"
              type="password"
              id="password"
              autoComplete="current-password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <Button type="submit" fullWidth variant="contained" sx={{ backgroundColor: '#e9531d', marginTop: '30px', color: 'white', '&:hover': { backgroundColor: '#FF7F50' } }}>
              LOG IN
            </Button>
          </Box>
        </div>
      </Container>
    </ThemeProvider>
  );
}

export default LoginForm;