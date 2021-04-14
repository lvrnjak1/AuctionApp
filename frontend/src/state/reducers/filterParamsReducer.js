const defaultState = {
    categoryId: null,
    sort: null,
    sortOrder: "ASC",
    page: 1,
    limit: 8,
    name: null,
    priceMin: null,
    priceMax: null
};

const filterParamsReducer = (state = defaultState, action) => {
    switch (action.type) {
        case 'SET_CATEGORY_ID':
            return { ...state, categoryId: action.payload.categoryId };
        case 'ADD_CATEGORY_ID':
            let cat = state.categoryId || [];
            if (!action.payload.categoryId) {
                cat = null;
            } else if (cat.find(c => c === action.payload.categoryId)) {
                cat = cat.filter(id => id !== action.payload.categoryId);
            } else {
                cat.push(action.payload.categoryId);
            }
            return { ...state, categoryId: cat };
        case 'REMOVE_CATEGORY_ID':
            let cat2 = state.categoryId || [];
            cat2 = cat2.filter(id => id !== action.payload.categoryId);
            return { ...state, categoryId: cat2, page: 1 };
        case 'SET_SORT':
            return { ...state, sort: action.payload.sort };
        case 'SET_SORT_ORDER':
            return { ...state, sortOrder: action.payload.sortOrder };
        case 'SET_PAGE':
            return { ...state, page: action.payload.page };
        case 'SET_LIMIT':
            return { ...state, limit: action.payload.limit };
        case 'SET_NAME':
            return { ...state, name: action.payload.name, page: 1 };
        case 'SET_PRICE_MIN':
            return { ...state, priceMin: action.payload.priceMin, page: 1 };
        case 'SET_PRICE_MAX':
            return { ...state, priceMax: action.payload.priceMax, page: 1 };
        case 'RESET':
            return defaultState;
        default:
            return state;
    }
}

export default filterParamsReducer;