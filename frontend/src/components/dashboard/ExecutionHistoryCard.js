import React from "react";
import { TableContainer, Table, TableHead, TableRow, TableCell, Paper, TableBody } from "@mui/material";

const executions = [

    {pipe: "benipintea/FirstProcessingPipe"},
    {pipe: "benipintea/JustTrim"},
    {pipe: "johndoe/CoolProcessingPipe"},
    {pipe: "benipintea/SecondProcessingPipe"},
]


export default function ExecutionHistoryCard() {
    return (
        <TableContainer component={Paper} >
            <Table>
                <TableHead sx={{ backgroundColor: "#f6f8fa" }}>
                    <TableRow>
                        <TableCell><b>Execution history</b></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    { executions.map( (item, index) => (
                        <TableRow key={index} sx={{ '&:last-child td, &:last-child th': { border: 0 }, '&:hover': {backgroundColor:'rgba(0, 0, 0, 0.04)'}  }} >
                            <TableCell component={"th"} scope="row">
                                    {item.pipe}
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>

    );
}