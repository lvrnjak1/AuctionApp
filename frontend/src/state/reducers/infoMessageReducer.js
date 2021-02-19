const defaultState = { message: "", className: "invisible" };

const infoMessageReducer = (state = defaultState, action) => {
    switch (action.type) {
        case 'SET_INFO_MESSAGE':
            return action.payload;
        case 'RESET_INFO_MESSAGE':
            return defaultState;
        default:
            return state;
    }
}

export default infoMessageReducer;