const setSuggestion = (suggestion) => {
    return {
        type: 'SET_SUGGESTION',
        payload: { suggestion }
    }
}

const resetSuggestion = () => {
    return {
        type: 'RESET_SUGGESTION'
    }
}

export { setSuggestion, resetSuggestion };