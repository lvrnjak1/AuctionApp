import axios from "axios";
import { setAsyncTaskInProgress } from "state/actions/asyncTaskInProgressActions";
import store from "state/store";
import { updateMessage } from "util/info_div_util";

const postRequest = async (endpoint, body, successHandler, errorHandler, requestConfig) => {
    try {
        store.dispatch(setAsyncTaskInProgress(true));
        const response = await axios.post(endpoint, body, requestConfig);
        store.dispatch(setAsyncTaskInProgress(false));
        successHandler(response);
    } catch (error) {
        errorHandler(error);
    }
}

const getRequest = async (endpoint, queryParams, successHandler, errorHandler) => {
    try {
        store.dispatch(setAsyncTaskInProgress(true));
        const response = await axios.get(endpoint, { params: queryParams });
        store.dispatch(setAsyncTaskInProgress(false));
        successHandler(response);
    } catch (error) {
        errorHandler(error);
    }
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
        store.dispatch(setAsyncTaskInProgress(false));
        let i = 0;
        requests.forEach(request => {
            request.successHandler(responses[i]);
            i++;
        });
    } catch (error) {
        updateMessage("Something went wrong, come back soon", "error");
    }
}

export {
    postRequest,
    getRequest,
    sendMultipleGetRequests
}