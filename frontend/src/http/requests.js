import axios from "axios"

const postRequest = async (endpoint, body, successHandler, errorHandler, requestConfig) => {
    try {
        const response = await axios.post(endpoint, body, requestConfig);
        successHandler(response);
    } catch (error) {
        errorHandler(error);
    }
}

export {
    postRequest
}