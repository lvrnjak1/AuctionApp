const setCurrentCategory = (category, subcategory) => {
    return {
        type: 'SET_CURRENT_CATEGORY',
        payload: { category, subcategory, doShow: true }
    }
}

const resetCurrentCategory = (doShow) => {
    return {
        type: 'RESET_CURRENT_CATEGORY',
        payload: { doShow }
    }
}

const initializeCurrentCategory = () => {
    return {
        type: 'INIT_CURRENT_CATEGORY'
    }
}

export { setCurrentCategory, resetCurrentCategory, initializeCurrentCategory }