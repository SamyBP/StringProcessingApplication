import { Button, Stack, FormControl, FormControlLabel, RadioGroup, Radio, TextField, Fab, Chip } from "@mui/material";
import { useState } from "react";
import AddIcon from "@mui/icons-material/Add"


const modules = [
    { name: "SUBSTRING" },
    { name: "TRIM" },
    { name: "UPPER" }, 
    { name: "LOWER" } 
]


export default function PipeCreationCard() {
    
    const [selectedModules, setSelectedModules] = useState([]);

    const isAddDisabled = selectedModules.length === 0;

    const addToSelectedModule = (event) => {
        const moduleName = event.target.value;
        setSelectedModules(prevSelectedModules => [...prevSelectedModules, moduleName]);
    };

    const removeSelectedModule = (idx) => {
        const temp = [...selectedModules];
        temp.splice(idx, 1);
        setSelectedModules(temp);
    }
     
    return (
        <Stack sx={{ margin: 2}} spacing={1}>
            
            <Stack direction={"row"} sx={{ display:"flex", alignItems: "center" }} spacing={ 1 }>
                <h3>Create a new pipe for benipintea</h3>
                <Fab sx={{backgroundColor: "#01579b", color: "white" }} aria-label="add" size="small" disabled={ isAddDisabled }>
                    <AddIcon />
                </Fab>
            </Stack>

            <FormControl>
                <RadioGroup row defaultValue="false">
                    <FormControlLabel value="false" control={<Radio />} label="Private" />
                    <FormControlLabel value="true" control={<Radio />} label="Public" />
                </RadioGroup>
            </FormControl>

            <TextField label="Pipe name" variant='outlined' placeholder='name your new pipe...' required style={{ width: 400 }}/>

            <Stack direction="row" spacing={2}>
                    {modules.map((item, index) => (
                        <Button key={index} value={item.name} onClick={addToSelectedModule} variant="outlined" sx={{color: "#01579b", borderColor: "lightgray"}}>
                             {item.name}
                        </Button>
                    ))}
            </Stack>

            <Stack direction="row" spacing={1} flexWrap={"wrap"} justifyContent={"flex-start"}>
                {selectedModules.map((module, index) => (
                    <Chip key={index} color="primary" variant="contained" label={module} onDelete={() => removeSelectedModule(index)} />
                ))}
            </Stack>
        </Stack>
    );
}