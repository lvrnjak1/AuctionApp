const defaultState = { message: "", className: "invisible" };

const infoMessageReducer = (state = defaultState, action) => {
    switch (action.type) {
        case 'SET':
            return action.payload;
        case 'RESET':
            return defaultState;
        default:
            return state;
    }
}

export default infoMessageReducer;