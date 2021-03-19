const categoriesReducer = (state = [], action) => {
    switch (action.type) {
        case 'SET_CATEGORIES':
            return action.payload.categories
        default:
            return state;
    }
}

export default categoriesReducer;