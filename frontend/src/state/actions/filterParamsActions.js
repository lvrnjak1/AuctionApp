const setCategoryId = (categoryId) => {
    return {
        type: 'SET_CATEGORY_ID',
        payload: { categoryId }
    }
}

const addCategoryId = (categoryId) => {
    return {
        type: 'ADD_CATEGORY_ID',
        payload: { categoryId }
    }
}

const removeCategoryId = (categoryId) => {
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
    return {
        type: 'SET_NAME',
        payload: { name }
    }
}

const setMinPrice = (priceMin) => {
    return {
        type: 'SET_PRICE_MIN',
        payload: { priceMin }
    }
}

const setMaxPrice = (priceMax) => {
    return {
        type: 'SET_PRICE_MAX',
        payload: { priceMax }
    }
}

const resetFilterParams = () => {
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