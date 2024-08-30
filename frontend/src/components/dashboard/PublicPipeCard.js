import React from "react";
import Stack from "@mui/material/Stack"
import {ListItem, ListItemText, ListItemButton } from "@mui/material";
import CodeIcon from "@mui/icons-material/Code"
import { Link } from "react-router-dom";

const PublicPipeCard = ({ publicPipes }) => {
    return (
        <Stack sx={{ margin: 2 }} spacing={1}>
            
            <h3>Public pipes</h3>

            {publicPipes.map((item, index) => (
                <ListItem key={index} disablePadding sx={{ display: 'flex', alignItems: "center" }}>
                    <CodeIcon sx={{ color:'#01579b' }}/>
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
        </ Stack>
    );
}

export default PublicPipeCard;
