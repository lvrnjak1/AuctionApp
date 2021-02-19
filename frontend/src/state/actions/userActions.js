const set = (user) => {
    return {
        type: 'SET',
        payload: user
    }
}

export { set };