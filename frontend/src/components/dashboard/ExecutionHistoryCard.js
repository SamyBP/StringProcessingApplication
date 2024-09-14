import React, { useEffect, useState } from "react";
import { TableContainer, Table, TableHead, TableRow, TableCell, Paper, TableBody } from "@mui/material";
import { Link } from "react-router-dom";
import PendingIcon from '@mui/icons-material/Pending';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import ErrorIcon from '@mui/icons-material/Error';
import { ExecutionService } from "../../services/execution.service";
import { Handler } from "../../services/request.util";

function StatusImage(props) {
    let status = props.status;
    let image;
    switch(status) {
        case 'FINISHED':
            image = <CheckCircleIcon sx={{color:'green' }} />
            break;
        case 'ERROR':
            image = <ErrorIcon sx={{ color:"#d1242f" }} />
            break;
        default:
            image = <PendingIcon sx={{ color: "gray" }} />        
    };

    return (
        <div>
            {image}
        </div>
    )
}


export default function ExecutionHistoryCard() {

    const [executions, setExecutions] = useState([]);

    useEffect(() => {
        ExecutionService.getExecutionHistory().then(executions => {
            setExecutions(executions);    
        })
        .catch(error => {
            Handler.handleError(error);
        })
    },[])


    return (
        <TableContainer component={Paper} >
            <Table>
                <TableHead sx={{ backgroundColor: "#f6f8fa" }}>
                    <TableRow>
                        <TableCell><b>Execution history</b></TableCell>
                        <TableCell />
                    </TableRow>
                </TableHead>
                <TableBody>
                    { executions.map( (item, index) => (
                        <TableRow key={index} sx={{ '&:last-child td, &:last-child th': { border: 0 }, '&:hover': {backgroundColor:'rgba(0, 0, 0, 0.04)'}  }} >
                            <TableCell component={"th"} scope="row" sx={{ height:30 }}>
                                <Link
                                    to='/execution-details'
                                    style={{ textDecoration: "none", color: "black" }}
                                    state={{ item }}
                                >
                                    {item.pipeName}
                                </Link>
                            </TableCell>
                            <TableCell component={"th"} scope="row" sx={{ height:30 }}>
                                <StatusImage status = {item.status} />
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}