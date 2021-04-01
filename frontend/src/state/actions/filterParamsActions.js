import { setFilterChanged } from "state/actions/filterChangeActions";
import store from "state/store";

const setCategoryId = (categoryId) => {
    store.dispatch(setFilterChanged(true));
    return {
        type: 'SET_CATEGORY_ID',
        payload: { categoryId }
    }
}

const addCategoryId = (categoryId) => {
    store.dispatch(setFilterChanged(true));
    return {
        type: 'ADD_CATEGORY_ID',
        payload: { categoryId }
    }
}

const removeCategoryId = (categoryId) => {
    store.dispatch(setFilterChanged(true));
    return {
        type: 'REMOVE_CATEGORY_ID',
        payload: { categoryId }
    }
}

const setSort = (sort) => {
    return {
        type: 'SET_SORT',
        payload: { sort }
    }
}

const setSortOrder = (sortOrder) => {
    return {
        type: 'SET_SORT_ORDER',
        payload: { sortOrder }
    }
}

const setPage = (page) => {
    return {
        type: 'SET_PAGE',
        payload: { page }
    }
}

const setLimit = (limit) => {
    return {
        type: 'SET_LIMIT',
        payload: { limit }
    }
}

const setName = (name) => {
    store.dispatch(setFilterChanged(true));
    return {
        type: 'SET_NAME',
        payload: { name }
    }
}

const setMinPrice = (priceMin) => {
    store.dispatch(setFilterChanged(false));
    return {
        type: 'SET_PRICE_MIN',
        payload: { priceMin }
    }
}

const setMaxPrice = (priceMax) => {
    store.dispatch(setFilterChanged(false));
    return {
        type: 'SET_PRICE_MAX',
        payload: { priceMax }
    }
}

const resetFilterParams = () => {
    store.dispatch(setFilterChanged(true));
    return {
        type: 'RESET'
    }
}

export {
    setCategoryId,
    addCategoryId,
    removeCategoryId,
    setSort,
    setSortOrder,
    setPage,
    setLimit,
    setName,
    setMinPrice,
    setMaxPrice,
    resetFilterParams
};