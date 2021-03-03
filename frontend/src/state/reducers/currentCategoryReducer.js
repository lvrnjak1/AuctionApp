const defaultState = { category: "All categories", subcategory: "", doShow: true };

const currentCategoryReducer = (state = defaultState, action) => {
    switch (action.type) {
        case 'INIT_CURRENT_CATEGORY':
            return defaultState;
        case 'SET_CURRENT_CATEGORY':
            return action.payload;
        case 'RESET_CURRENT_CATEGORY':
            let s = { ...defaultState, doShow: action.payload.doShow };
            return s;
        default:
            return state;
    }
}

export default currentCategoryReducer;