import { Button, Stack, FormControl, FormControlLabel, RadioGroup, Radio, TextField, Fab, Chip } from "@mui/material";
import { useState } from "react";
import AddIcon from "@mui/icons-material/Add"
import { useNavigate } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import { PipeService } from "../../services/pipe.service";
import { Handler } from "../../services/request.util";


const modules = [
    { id: 1, name: "SUBSTRING" },
    { id: 2, name: "TRIM" },
    { id: 3, name: "UPPER" }, 
    { id: 4, name: "LOWER" } 
]


export default function PipeCreationCard() {
    
    const navigate = useNavigate();
    const [selectedModules, setSelectedModules] = useState([]);
    const [isPublic, setIsPublic] = useState(false);
    const [pipeName, setPipeName] = useState('');

    const isAddDisabled = selectedModules.length === 0;

    const addToSelectedModule = (event) => {
        const module = JSON.parse(event.target.value);
        setSelectedModules(prevSelectedModules => [...prevSelectedModules, module]);
    };

    const removeSelectedModule = (idx) => {
        const temp = [...selectedModules];
        temp.splice(idx, 1);
        setSelectedModules(temp);
    }

    const handleIsPublicChange = (event) => {
        setIsPublic(event.target.value);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        PipeService.createPipe(pipeName, isPublic, selectedModules).then(pipe => {
           Handler.handleSuccess("Created new pipe!");
           navigate("/pipe", {state: {item: pipe}});     
        })
        .catch(error => {
            Handler.handleError(error);
        })
    }
     
    return (
        <Stack sx={{ margin: 2}} spacing={1}>
            
            <Stack direction={"row"} sx={{ display:"flex", alignItems: "center" }} spacing={ 1 }>
                <h2>Create a new pipe for {localStorage.getItem("username")} </h2>
                <Fab sx={{backgroundColor: "#01579b", color: "white" }} aria-label="add" size="small" disabled={ isAddDisabled } onClick={handleSubmit}>
                    <AddIcon />
                </Fab>
            </Stack>

            <FormControl>
                <RadioGroup row value={isPublic} onChange={handleIsPublicChange}>
                    <FormControlLabel value={false} control={<Radio />} label="Private" />
                    <FormControlLabel value={true} control={<Radio />} label="Public" />
                </RadioGroup>
            </FormControl>

            <TextField value={pipeName} onChange={(e) => setPipeName(e.target.value)} label="Pipe name" variant='outlined' placeholder='name your new pipe...' required fullWidth/>

            <h4>Choose modules for your pipe</h4>

            <Stack direction="row" spacing={2}>
                    {modules.map((item, index) => (
                        <Button key={index} value={JSON.stringify(item)} onClick={addToSelectedModule} variant="outlined" sx={{color: "#01579b", borderColor: "lightgray"}}>
                             {item.name}
                        </Button>
                    ))}
            </Stack>

            <Stack direction="row" spacing={1} flexWrap={"wrap"} justifyContent={"flex-start"} >
                {selectedModules.map((module, index) => (
                    <Chip 
                        size="small" color="primary" variant="contained"
                        key={index}  
                        label={module.name} 
                        onDelete={() => removeSelectedModule(index)}
                    />
                ))}
            </Stack>
            <ToastContainer />
        </Stack>
    );
}