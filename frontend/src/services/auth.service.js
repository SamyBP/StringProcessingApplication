import { BasicHeader, Endpoints } from "./util";
import { Handler } from "./request.util";

export const AuthService = {
    login,
    register,
    logout
}

function login(email, password) {
    let url = Endpoints.LOGIN;

    fetch(url, {
        method: 'POST',
        headers: BasicHeader(),
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
    .then(response => {
        if (response.status !== 200) {
            return Promise.reject(response);
        }

        return response.json();
    })
    .then(response => {
        localStorage.clear();
        localStorage.setItem('token', response.token);
        localStorage.setItem('email', email);
        localStorage.setItem('username', response.username);
        Handler.handleSuccess("Welcome!");
        window.location.href = '/dashboard';
    })
    .catch(error => {
        Handler.handleError(error);
    })
}

function register(username, email, password) {
    let url = Endpoints.REGISTER;

    fetch(url, {
        method: 'POST',
        headers: BasicHeader(),
        body: JSON.stringify({
            username: username,
            email: email,
            password: password
        })  
    })
    .then(response => {
        if (response.status !== 201) {
            return Promise.reject(response);
        }
        Handler.handleSuccess("Succesfully signed up!");
        window.location.href = '/login';
    })
    .catch(error => {
        Handler.handleError(error);
    })
}

function logout() {
    localStorage.clear();
    window.location.href = '/';
}