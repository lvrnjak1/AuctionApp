const asyncTaskInProgress = (state = false, action) => {
    switch (action.type) {
        case 'SET_ASYNC_IN_PROGRESS':
            return action.payload.inProgress;
        default:
            return state;
    }
}

export default asyncTaskInProgress;