import { Button, Stack, FormControl, FormControlLabel, RadioGroup, Radio, TextField, Fab, Chip } from "@mui/material";
import { useState } from "react";
import AddIcon from "@mui/icons-material/Add"
import { useNavigate } from "react-router-dom";


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

    const submitForm = (event) => {
        event.preventDefault();
        console.log(isPublic);
        console.log(pipeName);
        console.log(selectedModules.map(module => module.id));

        fetch('http://localhost:8080/api/pipes', {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                'token': localStorage.getItem('token')
              },
              body: JSON.stringify({
                name: pipeName,
                isPublic: isPublic,
                moduleIds: selectedModules.map(module => module.id)
              })  
        })
        .then(response => {
            if (response.status !== 201)
                return Promise.reject(response)

            return response.json();
        })
        .then(response => {
            console.log("Created a pipe!");
            navigate('/pipe', {state: {item: response} } )    
        })
        .catch(response => {
            response.json().then(json => {
                console.log(json.message);
            }) 
        })

    }
     
    return (
        <Stack sx={{ margin: 2}} spacing={1}>
            
            <Stack direction={"row"} sx={{ display:"flex", alignItems: "center" }} spacing={ 1 }>
                <h2>Create a new pipe for benipintea</h2>
                <Fab sx={{backgroundColor: "#01579b", color: "white" }} aria-label="add" size="small" disabled={ isAddDisabled } onClick={submitForm}>
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
        </Stack>
    );
}