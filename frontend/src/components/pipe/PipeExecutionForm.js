import { Button, Card, Divider, FormControl, FormControlLabel, Radio, RadioGroup, Stack, TextField, Typography } from "@mui/material";
import { useLocation } from "react-router-dom";

export default function PipeExecutionForm() {
    const location = useLocation();
    const { item } = location.state;

    return (

        <div style={{ display:'flex', justifyContent:'center', alignItems:'center' }}>
        <Card variant="outlined" sx={{ width: 1/2, marginTop:5 }} >
            <Stack sx={{ margin:2 }} spacing={1}>
                
                <h2> Execute pipe: {item.name}</h2>

                <Divider />

                <h3>Choose an execution version</h3>
                <FormControl>
                    <RadioGroup row>
                        <FormControlLabel value={1} control={<Radio />} label="Version 1" />
                        <FormControlLabel value={2} control={<Radio />} label="Version 2" />
                    </RadioGroup>
                </FormControl> 

                <TextField variant="outlined" size="small"  fullWidth label="Input" placeholder="Enter input" />

                {item.modules.map((module, index) => (
                    <Stack key={index}>
                        <h4>{module.name}</h4>
                        {Object.keys(module.args).length > 0 && (
                            <Stack direction={"row"} spacing={1}>
                                {Object.keys(module.args).map((arg) => (
                                <TextField
                                    variant="outlined"
                                    size="small"
                                    label={arg}
                                    sx={{ width: `calc( 100% / ${Object.keys(module.args).length} )` }}
                                />
                                ))}
                            </Stack>
                            )}
                    </Stack>     
                ))}

                <Button variant="contained" sx={{ backgroundColor: "#01579b" }}>Execute</Button>
            </Stack>
        </Card>
      </div>
    );
}