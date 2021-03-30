const filterChangeReducer = (state = true, action) => {
    switch (action.type) {
        case 'SET_CHANGED':
            return action.payload.changed;
        default:
            return state;
    }
}

export default filterChangeReducer;