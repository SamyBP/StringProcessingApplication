export const Endpoints = {
    LOGIN: LoginURL(),
    REGISTER: RegisterURL(),
    PIPE: PipeURL(),
    EXECUTIONS: ExecutionURL(),
    TRIGGER_EXECUTION: TriggerExecutionURL()
}

function BaseUrl() {
    return process.env.REACT_APP_BACKEND_BASE_URL;
}

function LoginURL() {
    return BaseUrl().concat("/api/auth/login");
}

function RegisterURL() {
    return BaseUrl().concat("/api/auth/register");
}

function PipeURL() {
    return BaseUrl().concat("/api/pipes");
}

function ExecutionURL() {
    return BaseUrl().concat('/api/executions');
}

function TriggerExecutionURL() {
    return BaseUrl().concat("/api/executions/private");
}

export function BasicHeader() {
    return new Headers({
        Accept: 'application/json',
        'Content-Type': 'application/json'
    });
}

export function TokenHeader() {
    return new Headers({
        Accept: 'application/json',
        'Content-Type': 'application/json',
        'token': localStorage.getItem('token')
    });
}