import { Card, Divider, Paper, Stack, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { useLocation } from "react-router-dom";
import Header from "../header/Header";

export default function PipeExecutionCard() {
    const location = useLocation();
    const {item} = location.state;

    return (
        <div style={{ display:'flex', justifyContent:'center', alignItems:'center', flexDirection:"column" }}>
            <Header />
            <Card sx={{ width: 4/5, margin: 2, border: 'none', boxShadow: 'none' }}>
                <Stack sx={{ margin: 2 }} spacing={1.5}>
                    
                    <h3>Execution for: {item.pipeName}</h3>

                    <Divider />

                    <TableContainer component={Paper}>
                        <Table>
                            <TableHead sx={{ backgroundColor: "#f6f8fa" }}>
                                <TableRow>
                                    <TableCell><b>Details</b></TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>

                                <TableRow sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                                    <TableCell component="th" scope="row"> 
                                        <b>Triggered at:</b> {item.createdAt}
                                    </TableCell>
                                </TableRow>

                                <TableRow sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                                    <TableCell component="th" scope="row"> 
                                    <b>Execution started at:</b> {item.startedAt}
                                    </TableCell>
                                </TableRow>

                                <TableRow sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                                    <TableCell component="th" scope="row"> 
                                        <b>Execution ended at:</b> {item.endedAt}
                                    </TableCell>
                                </TableRow>
                                
                                <TableRow sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                                    <TableCell component="th" scope="row"> 
                                        <b>Selected execution version:</b> {item.version}
                                    </TableCell>
                                </TableRow>
                                
                                <TableRow sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                                    <TableCell component="th" scope="row"> 
                                        <b>Input:</b> {item.input}
                                    </TableCell>
                                </TableRow>

                                <TableRow sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                                    <TableCell component="th" scope="row"> 
                                        <b>Status:</b> {item.status}
                                    </TableCell>
                                </TableRow>

                                <TableRow sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                                    <TableCell component="th" scope="row"> 
                                        <b>Result:</b> {item.result}
                                    </TableCell>
                                </TableRow>
                                
                            </TableBody>
                        </Table>
                    </TableContainer>

                </Stack>
            </Card>
          </div>
    );

}