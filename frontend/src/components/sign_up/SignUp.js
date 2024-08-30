import { Button, Paper, TextField, Typography, Container} from '@mui/material';
import { Link } from 'react-router-dom';

function SignUp() {
  return (
    <Container maxWidth="sm" align="center">
      <Paper elevation={10}>
        <Container maxWidth="sm">
            <h2>Sign up</h2>
        </Container>

        <Container>
            <TextField label="username" variant='standard' placeholder='username' fullWidth required style={{ margin: 10 }} />
        </Container>

        <Container>
            <TextField label="email" variant='standard' placeholder='email' fullWidth required style={{ margin: 10 }} />
        </Container>
        
        <Container>
            <TextField label="password" variant='standard' placeholder='password' fullWidth type='password' required style={{ margin: 10 }} />
        </Container>

        <Container>
            <Button variant='contained' color='primary' style={{ margin: 10 }} fullWidth >Sign up</Button>
        </Container>
        <Typography style={{ margin: 10 }}>
            <Link to="/login">Already have an account? Login.</Link>
        </Typography>

      </Paper>
    </Container>

  );
}

export default SignUp;
