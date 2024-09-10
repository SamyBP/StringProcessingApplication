import React, { useEffect, useState } from "react";
import { TableContainer, Table, TableHead, TableRow, TableCell, Paper, TableBody } from "@mui/material";


export default function ExecutionHistoryCard() {

    const [executions, setExecutions] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/executions', {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                'token': localStorage.getItem('token')
            },
        })
        .then(response => {
            if (response.status !== 200)
                return Promise.reject(response);
            return response.json();;
        })
        .then(response => {
            setExecutions(response);
        })
        .catch(response => {
            console.log(response.status);
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
                            <TableCell component={"th"} scope="row">
                                {item.pipeName}
                            </TableCell>
                            <TableCell component={"th"} scope="row">
                                {item.status}
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>

    );
}