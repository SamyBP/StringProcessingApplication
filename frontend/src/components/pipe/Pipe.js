import { Button, Card, Divider, IconButton, Paper, Stack, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import Chip from "@mui/material/Chip";
import PlayArrowIcon from "@mui/icons-material/PlayArrow"
import MoreVertRoundedIcon from '@mui/icons-material/MoreVertRounded';

export default function Pipe() {

    const navigate = useNavigate();    
    const location = useLocation();
    const { item } = location.state;

    console.log(item);

    const runPipe = (event) => {
        event.preventDefault();
        navigate('/execute', {state: {item: item} });
    }

    return (
        <div style={{ 
            display: 'flex', 
            justifyContent: 'center', 
            alignItems: 'flex-start', 
            minHeight: '100vh',
            paddingTop: '20px'
          }}>
            <Card sx={{ width: 4/5, margin: 2, border: 'none', boxShadow: 'none' }}>
              <Stack sx={{ margin: 2 }} spacing={1.5}>
                <Stack direction={"row"} sx={{ display: "flex", alignItems: "center" }} spacing={2}>
                    <h3>{item.name}</h3>
                    <Chip variant="outlined" size="small" label={item.isPublic ? "Public" : "Private"}  sx={{ color: 'gray' }}/>
                    <Stack direction={"row"} justifyContent="space-between" alignItems="center" sx={{ flexGrow: 1 }}>
                        
                        <br/>

                        <Stack direction="row" spacing={2}>
                            <Button variant="outlined" size="small" startIcon={ <PlayArrowIcon /> } sx={{ color: '#01579b', border: 'none' }} onClick={runPipe}>
                                Run
                            </Button>
                            { item.ownerUsername === localStorage.getItem('username') && 
                                <IconButton aria-label="settings">
                                    <MoreVertRoundedIcon sx={{ color: '#01579b' }} />
                                </IconButton> }
                        </Stack>
                    </Stack>
                </Stack>
                <Divider />

                <TableContainer component={Paper}>
                    <Table>
                        <TableHead sx={{ backgroundColor: "#f6f8fa" }}>
                            <TableRow>
                                <TableCell><b>Defined modules</b></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {item.modules.map((module, index) =>(
                                    <TableRow key={index}  sx={{ '&:last-child td, &:last-child th': { border: 0 } }} >
                                        <TableCell component="th" scope="row"> 
                                            {module.name}
                                        </TableCell>
                                    </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>

              </Stack>
            </Card>
          </div>
    );
}