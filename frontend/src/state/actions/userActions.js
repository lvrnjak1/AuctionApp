const setUser = (user) => {
    return {
        type: 'SET_USER',
        payload: user
    }
}

const resetUser = () => {
    return {
        type: 'RESET_USER'
    }
}

export { setUser, resetUser };