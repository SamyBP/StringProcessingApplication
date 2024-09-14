import { Button, Card, Divider, FormControl, FormControlLabel, Radio, RadioGroup, Stack, TextField } from "@mui/material";
import { useLocation, useNavigate } from "react-router-dom";
import Header from "../header/Header";
import { useState } from "react";
import { toast, ToastContainer } from "react-toastify";

export default function PipeExecutionForm() {    
    
    const navigate = useNavigate();
    const location = useLocation();
    const { item } = location.state;


    const [version, setVersion] = useState(1);
    const [input, setInput] = useState('');
    const [modules, setModules] = useState(
        item.modules.map((module) => ({
            name: module.name,
            args: {...module.args}
        }))
    );

    const moduleArgumentChange = (index, argumentName, value) => {
        const temp = [...modules];
        if (argumentName === 'start' || argumentName === 'end') {
          temp[index].args[argumentName] = parseInt(value);  
        } else {
          temp[index].args[argumentName] = value;
        }
        setModules(temp);
    }

    const submit = (event) => {
        event.preventDefault();
        
        let baseUrl = process.env.REACT_APP_BACKEND_BASE_URL;
        let executionUrl = baseUrl.concat("/api/executions/private");

        fetch(executionUrl, {
            method: 'POST',
            headers: {
              Accept: 'application/json',
              'Content-Type': 'application/json',
              'token': localStorage.getItem('token')
            },
            body: JSON.stringify({
              input: input,
              pipeId: item.id,
              version: version,
              modules: modules              
            })  
          })
          .then(response => {
            if (response.status !== 202) {
              return Promise.reject(response);
            }
            
            console.log("Triggered execution succefully!");
            navigate('/pipe', {state: {item: item}});
          })
          .catch(response => {
            console.log(response.status);
            response.json().then(json => {
              console.log(json.message);
              toast.error(json.message, {
                position: 'bottom-right',
              });
            }) 
          })   



    }

    return (

        <div style={{ display:'flex', justifyContent:'center', alignItems:'center', flexDirection:"column" }}>
            <Header />
            <Card variant="outlined" sx={{ width: 1/2, marginTop:5 }} >
                <Stack sx={{ margin:2 }} spacing={1}>
                    
                    <h2> Execute pipe: {item.name}</h2>

                    <Divider />

                    <h3>Choose an execution version</h3>
                    
                    <FormControl>
                        <RadioGroup row value={version} onChange={(e) => setVersion(e.target.value)}>
                            <FormControlLabel value={1} control={<Radio />} label="Version 1" />
                            <FormControlLabel value={2} control={<Radio />} label="Version 2" />
                        </RadioGroup>
                    </FormControl> 

                    <TextField value={input} onChange={(e) => setInput(e.target.value)} variant="outlined" size="small"  fullWidth label="Input" placeholder="Enter input" />

                    {item.modules.map((module, moduleIndex) => (
                        <Stack key={moduleIndex}>
                            <h4>{module.name}</h4>
                            {Object.keys(module.args).length > 0 && (
                                <Stack direction={"row"} spacing={1}>
                                    {Object.keys(module.args).map((arg, argIndex) => (
                                    <TextField
                                        key={argIndex}
                                        variant="outlined"
                                        size="small"
                                        label={arg}
                                        value={modules[moduleIndex].args[arg] || ""}
                                        onChange={(e) => moduleArgumentChange(moduleIndex, arg, e.target.value)}
                                        sx={{ width: `calc( 100% / ${Object.keys(module.args).length} )` }}
                                    />
                                    ))}
                                </Stack>
                                )}
                        </Stack>     
                    ))}

                    <Button variant="contained" sx={{ backgroundColor: "#01579b" }} onClick={submit}>Execute</Button>
                </Stack>
            </Card>
            <ToastContainer />
      </div>
    );
}