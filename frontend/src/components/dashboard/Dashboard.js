import { Stack, Card } from "@mui/material";
import SideMenu from "./SideMenu";
import PipeCreationCard from "./PipeCreationCard";
import PublicPipeCard from "./PublicPipeCard";
import ExecutionHistoryCard from "./ExecutionHistoryCard";

const pipes = [
    { name: 'benipintea/FirstProcessingPipe', userId: null, isPublic: false, modules: [ 'SUBSTRING', 'TRIM', 'LOWER' ] },
    { name: 'benipintea/SecondProcessingPipe', userId: null, isPublic: true, modules: [ 'LOWER', 'TRIM' ] },
    { name: 'benipintea/ThirdProcessingPipe', userId: null, isPublic: true, modules: [ 'UPPPER', 'SUBSTRING' ] },
    { name: 'benipintea/JustTrim', userId: null, isPublic: false, modules: [ 'TRIM' ] },

    { name: 'johndoe/CoolProcessingPipe', userId: 2, isPublic: true, modules: [ 'SUBSTRING', 'TRIM', 'LOWER' ] },
    { name: 'janedoe01/UsefullModulesForDayToDayTasks', userId: 3, isPublic: true, modules: [ 'LOWER', 'TRIM' ] },
    { name: 'janedoe01/ProcessingPipe', userId: 3, isPublic: true, modules: [ 'UPPPER', 'SUBSTRING' ] },
    { name: 'janedoe01/PublicPipe', userId: 3, isPublic: true, modules: [ 'TRIM' ] }

]

export default function Dashboard() {

    let  userPipes = pipes.filter((pipe) => {
        return pipe.userId === null;
    });

    let publicPipes = pipes.filter((pipe) => {
        return pipe.userId !== null && pipe.isPublic === true;
    });

    return(
        <Stack direction={"row"}>
                <SideMenu userPipes={userPipes} />
                <Stack spacing={5} marginTop={4} width={4/5} >
                    <Card variant="outlined" sx={{ width: 1/1 }}>
                        <PipeCreationCard />
                    </Card>
                    <Stack direction={"row"} spacing={2} alignItems={"center"} alignContent={"center"} >
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

