const setInfoMessage = (message, className) => {
    return {
        type: 'SET_INFO_MESSAGE',
        payload: { message, className }
    }
}

const resetInfoMessage = () => {
    return {
        type: 'RESET_INFO_MESSAGE'
    }
}

export { setInfoMessage, resetInfoMessage }