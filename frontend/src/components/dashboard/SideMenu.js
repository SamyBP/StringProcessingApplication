import React, { useEffect, useState } from 'react';
import MuiDrawer from "@mui/material/Drawer"
import drawerClasses from '@mui/material/Drawer';
import { styled }  from '@mui/material/styles';
import { Avatar, Divider, Stack, Typography } from '@mui/material';
import {List, ListItem, ListItemText, ListItemButton} from "@mui/material";
import { Link } from "react-router-dom";
import { PipeService } from '../../services/pipe.service';
import { Handler } from '../../services/request.util';

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

export default function SideMenu() {

    const [userOwnedPipes, setUserOwnedPipes] = useState([]);

    useEffect(() => {
        PipeService.getAllOwnedByUser().then(userOwnedPipes => {
            setUserOwnedPipes(userOwnedPipes);
        })
        .catch(error => {
            Handler.handleError(error);
        })
    }, []);

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
                    {userOwnedPipes.map((item, index) => (
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

        </Drawer>
    );
}