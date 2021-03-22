const setSearch = (search) => {
    return {
        type: 'SET_SEARCH',
        payload: { search }
    }
}

const resetSearch = () => {
    return {
        type: 'RESET_SEARCH'
    }
}

export { setSearch, resetSearch };