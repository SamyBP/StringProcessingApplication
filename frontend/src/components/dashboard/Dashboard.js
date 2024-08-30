import { Stack, Card } from "@mui/material";
import SideMenu from "./SideMenu";
import PipeCreationCard from "./PipeCreationCard";
import PublicPipeCard from "./PublicPipeCard";
import ExecutionHistoryCard from "./ExecutionHistoryCard";

const userPipes = [
    { name: 'benipintea/FirstProcessingPipe', isPublic: false, modules: [ 'SUBSTRING', 'TRIM', 'LOWER' ] },
    { name: 'benipintea/SecondProcessingPipe', isPublic: true, modules: [ 'LOWER', 'TRIM' ] },
    { name: 'benipintea/ThirdProcessingPipe', isPublic: true, modules: [ 'UPPPER', 'SUBSTRING' ] },
    { name: 'benipintea/JustTrim', isPublic: false, modules: [ 'TRIM' ] }
]

const publicPipes = [
    { name: 'johndoe/CoolProcessingPipe', isPublic: true, modules: [ 'SUBSTRING', 'TRIM', 'LOWER' ] },
    { name: 'janedoe01/UsefullModulesForDayToDayTasks', isPublic: true, modules: [ 'LOWER', 'TRIM' ] },
    { name: 'janedoe01/ProcessingPipe', isPublic: true, modules: [ 'UPPPER', 'SUBSTRING' ] },
    { name: 'janedoe01/PublicPipe', isPublic: true, modules: [ 'TRIM' ] }
]


export default function Dashboard() {
    return(
        <Stack direction={"row"}>
            <SideMenu userPipes={userPipes} />
            <Stack spacing={5} marginTop={4} width={4/5} >
                <Stack direction={"row"} spacing={2} alignItems={"center"} alignContent={"center"} >    
                    <Card variant="outlined" sx={{ width: 1/1, height: 1/1 }}>
                        <PipeCreationCard />
                    </Card>
                    
                    <Card variant="outlined" sx={{ width: 1/1, height: 1/1 }}>
                        <PublicPipeCard publicPipes={publicPipes} />
                    </Card>
                </Stack>
                <Card variant="outlined" sx={{ width: 1/1 }} >
                    <ExecutionHistoryCard />
                </Card>
            </Stack>
        </Stack>
    );
}

