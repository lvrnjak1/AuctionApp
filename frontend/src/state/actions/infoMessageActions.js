const set = (message, className) => {
    return {
        type: 'SET',
        payload: { message, className }
    }
}

const reset = () => {
    return {
        type: 'RESET'
    }
}

export { set, reset }