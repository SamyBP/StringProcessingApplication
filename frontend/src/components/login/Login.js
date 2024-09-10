import { Button, TextField, Typography,Stack, Card} from '@mui/material';
import { Link } from 'react-router-dom';


export default function Login() {

    return (
        <div style={{ display:'flex', justifyContent:'center', alignItems:'center' }}>
        <Card variant="outlined" sx={{ width: 1/2, marginTop:5 }} >
            <Stack spacing={5} alignContent={"center"} alignItems={"center"} margin={2} >
              <h2>Login</h2>

              <TextField label="email" variant='standard' placeholder='email' fullWidth required style={{ marginTop: 5 }} />
              <TextField label="password" variant='standard' placeholder='password' type='password' fullWidth required style={{ marginTop: 5 }} />
            
              <Button variant='contained' color='primary' style={{ marginTop: 10 }} fullWidth >Login</Button>

              <Typography style={{ marginTop: 10 }}>
                <Link to="/sign-up">Don't have an account? Sign-up.</Link>
              </Typography>

            </Stack>
        </Card>
      </div>
    );
}

