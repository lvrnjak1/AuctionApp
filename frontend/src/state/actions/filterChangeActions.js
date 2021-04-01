const setFilterChanged = (changed) => {
    return {
        type: 'SET_CHANGED',
        payload: { changed }
    }
}

export { setFilterChanged }