import axios from "axios";
import { setAsyncTaskInProgress } from "state/actions/asyncTaskInProgressActions";
import { setInfoMessage } from "state/actions/infoMessageActions";
import store from "state/store";

const postRequest = async (endpoint, body, successHandler, errorHandler, requestConfig) => {
    try {
        store.dispatch(setAsyncTaskInProgress(true));
        const response = await axios.post(endpoint, body, requestConfig);
        successHandler(response);
    } catch (error) {
        errorHandler(error);
    }

    store.dispatch(setAsyncTaskInProgress(false));
}

const getRequest = async (endpoint, queryParams, successHandler, errorHandler) => {
    try {
        store.dispatch(setAsyncTaskInProgress(true));
        const response = await axios.get(endpoint, { params: queryParams });
        successHandler(response);
    } catch (error) {
        errorHandler(error);
    }

    store.dispatch(setAsyncTaskInProgress(false));
}

const sendMultipleGetRequests = async (requests) => {
    store.dispatch(setAsyncTaskInProgress(true));
    const reqs = []
    requests.forEach(request => {
        reqs.push(
            axios.get(request.endpoint, { params: request.params })
        );
    })

    try {
        const responses = await axios.all(reqs);
        let i = 0;
        requests.forEach(request => {
            request.successHandler(responses[i]);
            i++;
        });
    } catch (error) {
        console.log(error);
        setTimeout(() => store.dispatch(setInfoMessage("Failed to connect to server, reload in a couple of minutes")), 5000);
    }

    store.dispatch(setAsyncTaskInProgress(false));
}

export {
    postRequest,
    getRequest,
    sendMultipleGetRequests
}