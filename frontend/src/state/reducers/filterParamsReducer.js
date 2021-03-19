const defaultState = { categoryId: null, sort: null, sortOrder: "ASC", page: 1, limit: 3, name: null };

const filterParamsReducer = (state = defaultState, action) => {
    switch (action.type) {
        case 'SET_CATEGORY_ID':
            return { ...state, categoryId: action.payload.categoryId };
        case 'SET_SORT':
            return { ...state, sort: action.payload.sort };
        case 'SET_SORT_ORDER':
            return { ...state, sortOrder: action.payload.sortOrder };
        case 'SET_PAGE':
            return { ...state, page: action.payload.page };
        case 'SET_LIMIT':
            return { ...state, limit: action.payload.limit };
        case 'SET_NAME':
            return { ...state, name: action.payload.name };
        case 'RESET':
            return defaultState;
        default:
            return state;
    }
}

export default filterParamsReducer;