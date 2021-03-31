const displayPreferenceReducer = (state = true, action) => {
    switch (action.type) {
        case 'SET_GRID':
            return true;
        case 'SET_LIST':
            return false;
        default:
            return state;
    }
}

export default displayPreferenceReducer;