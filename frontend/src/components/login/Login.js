import  Button  from "@mui/material/Button";
import  Paper  from "@mui/material/Paper";
import TextField from "@mui/material/TextField"
import { Typography, Container } from "@mui/material";
import { Link } from "react-router-dom"


export default function Login() {

    return (
        <Container maxWidth="sm" align="center">
            <Paper elevation={10}>
                <Container maxWidth="sm">
                    <h2>Login</h2>
                </Container>

                <Container>
                    <TextField label="email" variant='standard' placeholder='email' fullWidth required style={{ margin: 10 }} />
                </Container>
                
                <Container>
                    <TextField label="password" variant='standard' placeholder='password' fullWidth type='password' required style={{ margin: 10 }} />
                </Container>

                <Container>
                    <Button variant='contained' color='primary' style={{ margin: 10 }} fullWidth >Login</Button>
                </Container>
                <Typography style={{ margin: 10 }}>
                    <Link to = "/sign-up">Don't have an account? Sign up now.</Link>
                </Typography>
            </Paper>
        </Container>
    );
}

