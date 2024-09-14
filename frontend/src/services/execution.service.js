import { Handler } from "./request.util";
import { Endpoints, TokenHeader } from "./util"

export const ExecutionService = {
    getExecutionHistory,
    triggerExecution
}

function getExecutionHistory() {
    let url = Endpoints.EXECUTIONS;

    return fetch(url, {
        method: 'GET',
        headers: TokenHeader()
    })
    .then(response => {
        if (response.status !== 200) {
            return Promise.reject(response);
        }

        return response.json();
    })
}

function triggerExecution(input, pipeId, version, modules) {
    let url = Endpoints.TRIGGER_EXECUTION;

    console.log(input, pipeId, version, modules);

    fetch(url, {
        method: 'POST',
        headers: TokenHeader(),
        body: JSON.stringify({
            input: input,
            pipeId: pipeId,
            version: version,
            modules: modules
        })
    })
    .then(response => {
        if (response.status !== 202) {
            return Promise.reject(response);
        }
        Handler.handleSuccess("Succesfully triggered execution");
        window.location.href = "/dashboard";
    })
    .catch(error => {
        Handler.handleError(error);
    })
}