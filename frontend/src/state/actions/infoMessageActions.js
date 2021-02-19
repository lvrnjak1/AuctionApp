const set = (message) => {
    return {
        type: 'SET',
        payload: message
    }
}

const reset = () => {
    return {
        type: 'RESET'
    }
}

export { set, reset }