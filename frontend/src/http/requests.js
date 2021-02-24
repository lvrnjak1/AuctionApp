import axios from "axios"

const postRequest = async (endpoint, body, successHandler, errorHandler, requestConfig) => {
    try {
        const response = await axios.post(endpoint, body, requestConfig);
        successHandler(response);
    } catch (error) {
        errorHandler(error);
    }
}

const getRequest = async (endpoint, queryParams, successHandler, errorHandler) => {
    try {
        const response = await axios.get(endpoint, { params: queryParams });
        successHandler(response);
    } catch (error) {
        errorHandler(error);
    }
}

const sendMultipleGetRequests = async (requests) => {
    const reqs = []
    requests.forEach(request => {
        reqs.push(
            axios.get(request.endpoint, { params: request.params })
        );
    })

    try {
        const responses = await axios.all(reqs);
        console.log(responses);
        let i = 0;
        requests.forEach(request => {
            request.successHandler(responses[i]);
            i++;
        });
    } catch (error) {
        console.log(error);
    }
}

export {
    postRequest,
    getRequest,
    sendMultipleGetRequests
}