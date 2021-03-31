const defaultState = { value: [0, 0], min: 0, max: 0 }

const getNewValue = (val, min, max) => {
    if (val[0] < min || val[0] > max) {
        val[0] = min;
    }
    if (val[1] < min || val[1] > max) {
        val[1] = max;
    }

    return val;
}

const sliderReducer = (state = defaultState, action) => {
    switch (action.type) {
        case 'SET_SLIDER_VALUE':
            let val1 = getNewValue(action.payload.newValue, state.min, state.max);
            return { value: val1, min: state.min, max: state.max };
        case 'SET_SLIDER':
            const { value, min, max } = action.payload;
            let val2 = getNewValue(value, min, max);
            return { value: val2, min, max }
        case 'RESET_MIN':
            return { ...state, value: [state.min, state.value[1]] };
        case 'RESET_MAX':
            return { ...state, value: [state.value[0], state.max] };
        default:
            return state;
    }
}

export default sliderReducer;