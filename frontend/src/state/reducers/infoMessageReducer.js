const infoMessageReducer = (state = "Initial message", action) => {
    switch (action.type) {
        case 'SET':
            return action.payload;
        case 'RESET':
            return "";
        default:
            return state;
    }
}

export default infoMessageReducer;