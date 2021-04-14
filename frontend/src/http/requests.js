import axios from "axios";
import { setAsyncTaskInProgress } from "state/actions/asyncTaskInProgressActions";
import store from "state/store";
import { updateMessage } from "util/info_div_util";
import { UPLOAD_IMAGE_ENDPOINT } from "./endpoints";

const patchRequest = async (endpoint, body, params, successHandler, errorHandler, requestConfig) => {
    try {
        store.dispatch(setAsyncTaskInProgress(true));
        const response = await axios.patch(endpoint, body, { params, ...requestConfig });
        store.dispatch(setAsyncTaskInProgress(false));
        successHandler(response);
    } catch (error) {
        store.dispatch(setAsyncTaskInProgress(false));
        errorHandler(error);
    }
}

const postRequest = async (endpoint, body, successHandler, errorHandler, requestConfig) => {
    try {
        store.dispatch(setAsyncTaskInProgress(true));
        const response = await axios.post(endpoint, body, requestConfig);
        store.dispatch(setAsyncTaskInProgress(false));
        successHandler(response);
    } catch (error) {
        store.dispatch(setAsyncTaskInProgress(false));
        errorHandler(error);
    }
}

const getRequest = async (endpoint, queryParams, successHandler, errorHandler, requestConfig) => {
    try {
        store.dispatch(setAsyncTaskInProgress(true));
        const response = await axios.get(endpoint, { params: queryParams, ...requestConfig });
        store.dispatch(setAsyncTaskInProgress(false));
        successHandler(response);
    } catch (error) {
        store.dispatch(setAsyncTaskInProgress(false));
        errorHandler(error);
    }
}

const deleteRequest = async (endpoint, queryParams, successHandler, errorHandler, requestConfig) => {
    try {
        store.dispatch(setAsyncTaskInProgress(true));
        const response = await axios.delete(endpoint, { params: queryParams, ...requestConfig });
        store.dispatch(setAsyncTaskInProgress(false));
        successHandler(response);
    } catch (error) {
        store.dispatch(setAsyncTaskInProgress(false));
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
        store.dispatch(setAsyncTaskInProgress(false));
        updateMessage("Try reloading the page.", "error");
    }
}

const uploadFormData = async (endpoint, data, successHandler, errorHandler) => {
    try {
        const response = await axios.post(endpoint, data, { headers: { "Content-Type": "multipart/form-data" } });
        successHandler(response);
    } catch (error) {
        errorHandler(error);
    }
}

const uploadMultipleImages = async (images, successHandler) => {
    store.dispatch(setAsyncTaskInProgress(true));
    const reqs = []
    images.forEach(image => {
        const formData = new FormData();
        formData.append("file", image.data_url);
        formData.append('upload_preset', 'upload_preset');
        reqs.push(
            axios.post(UPLOAD_IMAGE_ENDPOINT, formData, { headers: { "Content-Type": "multipart/form-data" } })
        );
    });

    try {
        const responses = await axios.all(reqs);
        store.dispatch(setAsyncTaskInProgress(false));
        successHandler(responses);
    } catch (error) {
        store.dispatch(setAsyncTaskInProgress(false));
        updateMessage("Images failed to upload, try uploading again", "error");
    }
}

export {
    postRequest,
    getRequest,
    sendMultipleGetRequests,
    patchRequest,
    uploadFormData,
    uploadMultipleImages,
    deleteRequest
}