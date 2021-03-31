const setSliderValue = (newValue) => {
    return {
        type: 'SET_SLIDER_VALUE',
        payload: { newValue }
    }
}

const setSlider = (value, min, max) => {
    return {
        type: 'SET_SLIDER',
        payload: { value, min, max }
    }
}

const resetMin = () => {
    return {
        type: 'RESET_MIN'
    }
}

const resetMax = () => {
    return {
        type: 'RESET_MAX'
    }
}

export { setSliderValue, setSlider, resetMin, resetMax };