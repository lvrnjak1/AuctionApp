const setCategories = (categories) => {
    return {
        type: 'SET_CATEGORIES',
        payload: { categories }
    }
}

export { setCategories }