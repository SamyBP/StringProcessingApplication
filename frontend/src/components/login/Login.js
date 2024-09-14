import { Button, TextField, Typography,Stack, Card} from '@mui/material';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { AuthService } from '../../services/auth.service';

export default function Login() {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = (event) => {
      event.preventDefault();
      AuthService.login(email, password);
    }    

    return (
        <div style={{ display:'flex', justifyContent:'center', alignItems:'center' }}>
        <Card variant="outlined" sx={{ width: 1/2, marginTop:5 }} >
            <Stack spacing={5} alignContent={"center"} alignItems={"center"} margin={2} >
              <h2>Login</h2>

              <TextField value={email} onChange={(e) => setEmail(e.target.value)} label="email" variant='standard' placeholder='email' fullWidth required style={{ marginTop: 5 }} />
              <TextField value={password} onChange={(e) => setPassword(e.target.value)} label="password" variant='standard' placeholder='password' type='password' fullWidth required style={{ marginTop: 5 }} />
            
              <Button variant='contained' color='primary' style={{ marginTop: 10 }} fullWidth onClick={handleSubmit}>Login</Button>

              <Typography style={{ marginTop: 10 }}>
                <Link to="/sign-up">Don't have an account? Sign-up.</Link>
              </Typography>

            </Stack>
        </Card>
        <ToastContainer />
      </div>
    );
}

