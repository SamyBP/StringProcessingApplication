import { Button, TextField, Typography,Stack, Card} from '@mui/material';
import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';


export default function Login() {

    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const login = (e) => {
      e.preventDefault();
      
      fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          email: email,
          password: password
        })  
      })
      .then(response => {
        if (response.status !== 200) {
          return Promise.reject(response);
        }
        
        return response.json();
      })
      .then(response => {
        console.log(response.expiresIn);
        console.log(response.token);
        localStorage.setItem('token', response.token);
        localStorage.setItem('username', response.username);
        localStorage.setItem('email', email);
        navigate('/dashboard');
      })
      .catch(response => {
        console.log(response.status);
        response.json().then(json => {
          console.log(json.message);
        }) 
      })   

    }

    return (
        <div style={{ display:'flex', justifyContent:'center', alignItems:'center' }}>
        <Card variant="outlined" sx={{ width: 1/2, marginTop:5 }} >
            <Stack spacing={5} alignContent={"center"} alignItems={"center"} margin={2} >
              <h2>Login</h2>

              <TextField value={email} onChange={(e) => setEmail(e.target.value)} label="email" variant='standard' placeholder='email' fullWidth required style={{ marginTop: 5 }} />
              <TextField value={password} onChange={(e) => setPassword(e.target.value)} label="password" variant='standard' placeholder='password' type='password' fullWidth required style={{ marginTop: 5 }} />
            
              <Button variant='contained' color='primary' style={{ marginTop: 10 }} fullWidth onClick={login}>Login</Button>

              <Typography style={{ marginTop: 10 }}>
                <Link to="/sign-up">Don't have an account? Sign-up.</Link>
              </Typography>

            </Stack>
        </Card>
      </div>
    );
}

