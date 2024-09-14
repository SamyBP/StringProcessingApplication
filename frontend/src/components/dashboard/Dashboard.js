import { Stack, Card } from "@mui/material";
import SideMenu from "./SideMenu";
import PipeCreationCard from "./PipeCreationCard";
import PublicPipeCard from "./PublicPipeCard";
import ExecutionHistoryCard from "./ExecutionHistoryCard";

export default function Dashboard() {

    return(
        <Stack direction={"row"}>
                <SideMenu />
                <Stack spacing={5} marginTop={4} width={4/5} >
                    <Card variant="outlined" sx={{ width: 1/1 }}>
                        <PipeCreationCard />
                    </Card>
                    <Stack direction={"row"} spacing={2} alignItems={"stretch"} alignContent={"center"} >
                        <Card variant="outlined" sx={{ width: 1/2 }} >
                            <ExecutionHistoryCard />
                        </Card>
                        <Card variant="outlined" sx={{ width: 1/2 }}>
                            <PublicPipeCard />
                        </Card>
                    </Stack>
                </Stack>
        </Stack>
    );
}

