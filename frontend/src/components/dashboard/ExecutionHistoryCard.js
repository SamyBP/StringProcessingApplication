import React from "react";
import { TableContainer, Table, TableHead, TableRow, TableCell, Paper, TableBody } from "@mui/material";

const executions = [

    {pipe: "benipintea/FirstProcessingPipe", status: 'FINISHED'},
    {pipe: "benipintea/JustTrim", status: 'FINISHED'},
    {pipe: "johndoe/CoolProcessingPipe", status: 'FINISHED'},
    {pipe: "benipintea/SecondProcessingPipe", status: 'ERROR'},
]


export default function ExecutionHistoryCard() {
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
                                {item.pipe}
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