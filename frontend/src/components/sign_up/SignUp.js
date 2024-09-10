import { Button, TextField, Typography,Stack, Card} from '@mui/material';
import { Link } from 'react-router-dom';
import { useState } from 'react';

function SignUp() {

  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const register = (e) => {
    e.preventDefault();
    console.log(username, email, password);

    fetch('http://localhost:8080/api/auth/register', {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        username: username,
        email: email,
        password: password
      })  
    })
    .then(response => {
      if (response.status !== 201) {
        return Promise.reject(response);
      }
      console.log("Succesfully signed up!");
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
            <h2>Sign up</h2>

              <TextField value={username} onChange={(e) => setUsername(e.target.value)} label="username" variant='standard' placeholder='username' fullWidth required style={{ marginTop: 5 }} />
              <TextField value={email} onChange={(e) => setEmail(e.target.value)} label="email" variant='standard' placeholder='email' fullWidth required style={{ marginTop: 5 }} />
              <TextField value={password} onChange={(e) => setPassword(e.target.value)} label="password" variant='standard' placeholder='password' type='password' fullWidth required style={{ marginTop: 5 }} />
              
              <Button variant='contained' color='primary' style={{ marginTop: 10 }} fullWidth onClick={register}>Sign up</Button>

              <Typography style={{ marginTop: 10 }}>
                <Link to="/login">Already have an account? Login.</Link>
              </Typography>

          </Stack>      
        </Card>
      </div>
  );
}

export default SignUp;
