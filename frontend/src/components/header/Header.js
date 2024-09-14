import { AppBar, Box, Button, IconButton, Toolbar, Typography } from "@mui/material";
import HomeIcon from '@mui/icons-material/Home';
import { AuthService } from "../../services/auth.service";

export default function Header() {

    const handleLogout = (e) => {
        AuthService.logout();
    }

    const goToDashboard = (e) => {
        window.location.href = "/dashboard";
    }

    return(
        <Box sx={{ flexGrow: 1, width:"100%" }}>
            <AppBar position="static" sx={{ backgroundColor:"#01579b" }}>
                <Toolbar>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        sx={{ mr: 5 }}
                        onClick={goToDashboard}
                    >
                        <HomeIcon />
                    </IconButton>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        String Processing Application
                    </Typography>
                
                    <Button color="inherit" onClick={handleLogout}>Logout</Button>
                </Toolbar>
            </AppBar>
        </Box>
    );
}