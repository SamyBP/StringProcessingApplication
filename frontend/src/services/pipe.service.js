import { Handler } from "./request.util";
import { Endpoints, TokenHeader, } from "./util";

export const PipeService = {
    createPipe,
    getAllOwnedByUser,
    getAllPublicPipes,
    update,
    remove
}

function createPipe(name, isPublic, modules) {
    let url = Endpoints.PIPE;
    
    return fetch(url, {
        method: 'POST',
        headers: TokenHeader(),
        body: JSON.stringify({
            name: name,
            isPublic: isPublic,
            moduleIds: modules.map(module => module.id)
        })
    })
    .then(response => {
        if (response.status !== 201) {
            return Promise.reject(response);
        }

        return response.json();
    })
}

function getAllOwnedByUser() {
    return getAllWithCondition(pipe => {
        return pipe.ownerUsername === localStorage.getItem('username');
    })
}

function getAllPublicPipes() {
    return getAllWithCondition(pipe => {
        return pipe.ownerUsername !== localStorage.getItem('username') && pipe.isPublic === true;
    });
}

function getAllWithCondition(conditon) {
    let url = Endpoints.PIPE;
    return fetch(url, {
        method: "GET",
        headers: TokenHeader()
    })
    .then(response => {
        if (response.status !== 200) {
            return Promise.reject(response);
        }

        return response.json();
    })
    .then(pipes => {
        return pipes.filter(conditon);
    })
}

function update(pipeId, isPublic, modules) {
    let url = Endpoints.PIPE;

    return fetch(url, {
        method: 'PUT',
        headers: TokenHeader(),
        body: JSON.stringify({
            pipeId: pipeId,
            isPublic: isPublic,
            moduleIds: modules.map(module => module.id)
        })
    })
    .then(response => {
        if (response.status !== 201) {
            return Promise.reject(response);
        }

        return response.json();
    })
}

function remove(pipeId) {
    let url = Endpoints.PIPE;

    fetch(`${url}/${pipeId}`, {
        method: 'DELETE',
        headers: TokenHeader()
    })
    .then(response => {
        if (response.status !== 204) {
            return Promise.reject(response);
        }

        Handler.handleSuccess("Deleted pipe!");
        window.location.href = "/dashboard";
    })
    .catch(error => {
        Handler.handleError(error);
    })
}