import { useLocation, useNavigate } from "react-router-dom";
import Header from "../header/Header";
import { Button, Card, Chip, Divider, FormControl, FormControlLabel, Radio, RadioGroup, Stack, Typography } from "@mui/material";
import { useState } from "react";
import { toast, ToastContainer } from "react-toastify";

const modules = [
    { id: 1, name: "SUBSTRING" },
    { id: 2, name: "TRIM" },
    { id: 3, name: "UPPER" }, 
    { id: 4, name: "LOWER" } 
]

export default function PipeSettings() {
    const location = useLocation();
    const { item } = location.state;

    const [isPublic, setIsPublic] = useState(item.isPublic);
    const [selectedModules, setSelectedModules] = useState([]);

    const navigate = useNavigate();

    const isEditButtonDisabled = selectedModules.length === 0;

    const handleIsPublicChange = (event) => {
        setIsPublic(event.target.value);
    }

    const addToSelectedModule = (event) => {
        const module = JSON.parse(event.target.value);
        setSelectedModules(prevSelectedModules => [...prevSelectedModules, module]);
    };

    const removeSelectedModule = (idx) => {
        const temp = [...selectedModules];
        temp.splice(idx, 1);
        setSelectedModules(temp);
    }

    const updatePipe = (event) => {
        event.preventDefault();
        console.log(isPublic);
        console.log(selectedModules);

        fetch('http://localhost:8080/api/pipes', {
            method: 'PUT',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                'token': localStorage.getItem('token')
              },
              body: JSON.stringify({
                pipeId: item.id,
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
            console.log("Updated pipe!");
            navigate('/pipe', {state: {item: response} } )    
        })
        .catch(response => {
            response.json().then(json => {
                console.log(json.message);
                toast.error(json.message, {
                    position: 'bottom-right',
                });
            }) 
        })

    }

    const deletePipe = (event) => {
        event.preventDefault();

        fetch(`http://localhost:8080/api/pipes/${item.id}`, {
            method: 'DELETE',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                'token': localStorage.getItem('token')
              },
        })
        .then(response => {
            if (response.status !== 204)
                return Promise.reject(response)

            console.log("Deleted pipe!");
            navigate('/dashboard');
        })
        .catch(response => {
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
                <div style={{ display:"flex", alignItems:'center', justifyContent: 'space-between'}}>
                    <div style={{ margin:10, display:"flex", flexDirection:'column' }}>
                        <Typography variant="body2" sx={{ fontWeight: 500, lineHeight: '24px' }}> 
                            Edit this pipe
                        </Typography>
                        <Typography variant="caption" sx={{ color: 'text.secondary' }}>
                            Changes will take effect after submision
                        </Typography>
                        
                    </div>
                    <Button 
                        size="small"
                        variant="contained"
                        sx={{margin: 1, borderColor: '#01579b', backgroundColor: '#01579b'}}
                        onClick={updatePipe}
                        disabled={isEditButtonDisabled}
                    >
                        Edit
                    </Button>
                </div>

                <Divider />

                <div style={{ display:"flex", alignItems:'center', justifyContent: 'space-between'}}>
                    <div style={{ margin:10, display:"flex", flexDirection:'column' }}>
                        <Typography variant="body2" sx={{ fontWeight: 500, lineHeight: '24px' }}> 
                            Change pipe visibility    
                        </Typography>
                        <Typography variant="caption" sx={{ color: 'text.secondary' }}>
                            This pipe is currently {item.isPublic ? 'public' :  'private'}
                        </Typography>    
                    </div>
                    <FormControl>
                        <RadioGroup row value={isPublic} onChange={handleIsPublicChange}>
                            <FormControlLabel value={false} control={<Radio />} label="Private" />
                            <FormControlLabel value={true} control={<Radio />} label="Public" />
                        </RadioGroup>
                    </FormControl>
                </div>  

                <Divider />

                <div style={{ display:"flex", alignItems:'center', justifyContent: 'space-between'}}>
                    <div style={{ margin:10, display:"flex", flexDirection:'column' }}>
                        <Typography variant="body2" sx={{ fontWeight: 500, lineHeight: '24px' }}> 
                            Change pipe definition    
                        </Typography>
                        <Typography variant="caption" sx={{ color: 'text.secondary' }}>
                            Select modules for pipe
                        </Typography>    
                    </div>

                    <Stack direction="row" spacing={2} marginRight={1}>
                        {modules.map((item, index) => (
                            <Button 
                                key={index} 
                                onClick={addToSelectedModule} 
                                size="small" 
                                value={JSON.stringify(item)} 
                                variant="outlined" 
                                sx={{color: "#01579b", borderColor: "#01579b"}}
                            >
                                {item.name}
                            </Button>
                        ))}
                    </Stack>
                </div>

                <Stack 
                    marginLeft={1} 
                    marginRight={1} 
                    marginBottom={1} 
                    direction="row" 
                    spacing={1} 
                    flexWrap={"wrap"} 
                    justifyContent={"flex-start"} 
                >
                    {selectedModules.map((module, index) => (
                        <Chip 
                            size="small" color="primary" variant="contained"
                            key={index}  
                            label={module.name} 
                            onDelete={() => removeSelectedModule(index)}
                        />
                    ))}
                </Stack>

            </Card>

            <Card variant="outlined" sx={{ width: 1/2, marginTop:5 }} >
                <div style={{ display:"flex", alignItems:'center', justifyContent: 'space-between'}}>
                    <div style={{ margin:10, display:"flex", flexDirection:'column' }}>
                        <Typography variant="body2" sx={{ fontWeight: 500, lineHeight: '24px' }}> 
                            Delete this pipe
                        </Typography>
                        <Typography variant="caption" sx={{ color: 'text.secondary' }}>
                            Once you delete this pipe there is no going back
                        </Typography>
                    </div>
                    <Button
                        variant="outlined"
                        size="small"  
                        sx={{borderColor: "#d1242f", color:"#d1242f", height:30, margin: 1}}
                        onClick={deletePipe}
                    >
                        Delete this pipe
                    </Button>
                </div>
            </Card>
            <ToastContainer />
        </div>
    );

}