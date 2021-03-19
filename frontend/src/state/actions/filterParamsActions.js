const setCategoryId = (categoryId) => {
    return {
        type: 'SET_CATEGORY_ID',
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

const resetFilterParams = () => {
    return {
        type: 'RESET'
    }
}

export { setCategoryId, setSort, setSortOrder, setPage, setLimit, setName, resetFilterParams };