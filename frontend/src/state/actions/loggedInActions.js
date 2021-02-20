const setLoggedIn = () => {
    return {
        type: 'SET_USER'
    }
}

const resetLoggedIn = () => {
    return {
        type: 'RESET_USER'
    }
}

export { setLoggedIn, resetLoggedIn };