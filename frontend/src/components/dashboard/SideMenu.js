import React from 'react';
import MuiDrawer from "@mui/material/Drawer"
import drawerClasses from '@mui/material/Drawer';
import { styled }  from '@mui/material/styles';
import { Avatar, Button, Divider, Stack, Typography } from '@mui/material';
import {List, ListItem, ListItemText, ListItemButton} from "@mui/material";
import { Link, useNavigate } from "react-router-dom";

const drawerWidth = 300;

const Drawer = styled(MuiDrawer)({
  width: drawerWidth,
  flexShrink: 0,
  boxSizing: 'border-box',
  mt: 10,
  [`& .${drawerClasses.paper}`]: {
    width: drawerWidth,
    boxSizing: 'border-box',
  },
});

const SideMenu = ({ userPipes }) => {

    const navigate = useNavigate();

    const logOut = (e) => {
        localStorage.clear();
        navigate("/");
    }

    return (
        <Drawer
            variant="permanent"
            sx={{
                display: { xs: 'none', md: 'block' },
                [`& .${drawerClasses.paper}`]: {
                backgroundColor: 'background.paper',
                },
            }}
        >

            <Stack direction={'row'} spacing={2} sx={{ margin:2 }}>
                <Avatar sx={{ width: 40, height: 40, bgcolor: "#01579b" }}> {localStorage.getItem('username').charAt(0).toUpperCase()} </ Avatar> 
                <Stack>
                    <Typography variant="body2" sx={{ fontWeight: 500, lineHeight: '24px' }}> 
                        {localStorage.getItem("username")}
                    </Typography>
                    <Typography variant="caption" sx={{ color: 'text.secondary' }}>
                        {localStorage.getItem("email")}
                    </Typography>
                </Stack>  
            </Stack>

            <Divider /> 

            <Stack sx={{ flexGrow: 1, p: 1, justifyContent: 'space-between' }}>
                <List dense>
                    {userPipes.map((item, index) => (
                        <ListItem key={index} disablePadding sx={{ display: 'block' }}>
                            <ListItemButton>
                                <Link 
                                    to='/pipe' 
                                    style={{ textDecoration: "none", color: "black" }}
                                    state={{ item }}
                                >
                                    <ListItemText primary={item.name} /> 
                                </Link> 
                            </ListItemButton>
                        </ListItem>
                    ))}
                </List>
            </ Stack>

            <Button variant='contained' sx={{ backgroundColor: "#01579b", margin:1 }} onClick={logOut}>Log out</Button>        

        </Drawer>
    );
}

export default SideMenu;