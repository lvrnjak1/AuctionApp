const defaultState = { category: "All categories", subcategory: "", doShow: false };

const currentCategoryReducer = (state = defaultState, action) => {
    switch (action.type) {
        case 'INIT_CURRENT_CATEGORY':
            let s1 = { ...state, doShow: true };
            return s1;
        case 'SET_CURRENT_CATEGORY':
            return action.payload;
        case 'RESET_CURRENT_CATEGORY':
            let s2 = { ...state, doShow: action.payload.doShow };
            return s2;
        default:
            return state;
    }
}

export default currentCategoryReducer;