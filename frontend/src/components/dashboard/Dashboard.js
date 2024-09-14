import { Stack, Card } from "@mui/material";
import SideMenu from "./SideMenu";
import PipeCreationCard from "./PipeCreationCard";
import PublicPipeCard from "./PublicPipeCard";
import ExecutionHistoryCard from "./ExecutionHistoryCard";
import { useEffect, useState } from "react";

export default function Dashboard() {

    const [pipes, setPipes] = useState([]);

    useEffect(() => {
        let baseUrl = process.env.REACT_APP_BACKEND_BASE_URL;
        let pipeUrl = baseUrl.concat("/api/pipes");
        console.log(pipeUrl);
        fetch(pipeUrl, {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
                'token': localStorage.getItem('token')
            },
        })
        .then(response => {
            if (response.status !== 200)
                return Promise.reject(response);
            return response.json();;
        })
        .then(response => {
            setPipes(response);
        })
        .catch(response => {
            console.log(response.status);
        })
    },[])

    let  userPipes = pipes.filter((pipe) => {
        return pipe.ownerUsername === localStorage.getItem('username');
    });

    let publicPipes = pipes.filter((pipe) => {
        return pipe.ownerUsername !== localStorage.getItem('username') && pipe.isPublic === true;
    });

    return(
        <Stack direction={"row"}>
                <SideMenu userPipes={userPipes} />
                <Stack spacing={5} marginTop={4} width={4/5} >
                    <Card variant="outlined" sx={{ width: 1/1 }}>
                        <PipeCreationCard />
                    </Card>
                    <Stack direction={"row"} spacing={2} alignItems={"stretch"} alignContent={"center"} >
                        <Card variant="outlined" sx={{ width: 1/2 }} >
                            <ExecutionHistoryCard />
                        </Card>
                        <Card variant="outlined" sx={{ width: 1/2 }}>
                            <PublicPipeCard publicPipes={publicPipes} />
                        </Card>
                    </Stack>
                </Stack>
        </Stack>
    );
}

