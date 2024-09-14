import { toast } from "react-toastify"

export const Handler = {
    handleError,
    handleSuccess,
}

function handleError(error) {
    error.json().then(json => {
        toast.error(json.message, {
            position: 'bottom-right',
        });
    })
}

function handleSuccess(message) {
    toast.success(message, { 
        position: 'bottom-right',
    });
    delay(2000);
}

function delay(timeout) {
    return new Promise(resolve => setTimeout(resolve, timeout));
}