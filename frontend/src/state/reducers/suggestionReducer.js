const suggestionReducer = (state = "Shoes", action) => {
    switch (action.type) {
        case 'SET_SUGGESTION':
            return action.payload.suggestion;
        case 'RESET_SUGGESTION':
            return null;
        default:
            return state;
    }
}

export default suggestionReducer;