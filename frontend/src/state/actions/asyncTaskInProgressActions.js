const setAsyncTaskInProgress = (inProgress) => {
    return {
        type: 'SET_ASYNC_IN_PROGRESS',
        payload: { inProgress }
    }
}

export { setAsyncTaskInProgress }