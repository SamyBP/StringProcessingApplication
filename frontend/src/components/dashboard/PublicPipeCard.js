import React, { useEffect, useState } from "react";
import TableContainer from "@mui/material/TableContainer";
import Table from "@mui/material/Table"
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import Paper from "@mui/material/Paper";
import TableBody from "@mui/material/TableBody";
import {Link} from "react-router-dom";
import { PipeService } from "../../services/pipe.service";
import { Handler } from "../../services/request.util";


export default function PublicPipeCard() {

    const [publicPipes, setPublicPipes] = useState([]);

    useEffect(() => {
        PipeService.getAllPublicPipes().then(pipes => {
            setPublicPipes(pipes);
        })
        .catch(error => {
            Handler.handleError(error);
        })
    }, [])

    return (
        <TableContainer component={Paper} >
            <Table>
                <TableHead sx={{ backgroundColor: "#f6f8fa" }}>
                    <TableRow>
                        <TableCell><b>Public pipes</b></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    { publicPipes.map( (item, index) => (
                        <TableRow key={index} sx={{ '&:last-child td, &:last-child th': { border: 0 }, '&:hover': {backgroundColor:'rgba(0, 0, 0, 0.04)'} }} >
                            <TableCell component={"th"} scope="row" sx={{ height:30 }}>
                                <Link
                                    to='/pipe'
                                    style={{ textDecoration: "none", color: "black" }}
                                    state={{ item }}
                                >
                                    {item.name}
                                </Link>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
