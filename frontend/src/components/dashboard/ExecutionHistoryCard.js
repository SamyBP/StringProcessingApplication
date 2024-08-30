import React from "react";
import Stack from "@mui/material/Stack"
import {ListItem, ListItemText, ListItemButton } from "@mui/material";
import CodeIcon from "@mui/icons-material/Code"

const executions = [

    {pipe: "benipintea/FirstProcessingPipe"},
    {pipe: "benipintea/JustTrim"},
    {pipe: "johndoe/CoolProcessingPipe"},
    {pipe: "benipintea/SecondProcessingPipe"},
    {pipe: "benipintea/FirstProcessingPipe"},
]


export default function ExecutionHistoryCard() {
    return (
        <Stack sx={{ margin: 2 }} spacing={1}>
            
            <h3>Execution history</h3>

            {executions.map((item, index) => (
                <ListItem key={index} disablePadding sx={{ display: 'flex', alignItems: "center" }}>
                    <CodeIcon sx={{ color:'#01579b' }}/>
                    <ListItemButton>
                                <ListItemText primary={item.pipe} />  
                    </ListItemButton>
                </ListItem>    
            ))}    
        </ Stack>
    );
}