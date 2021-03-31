const defaultState = { value: [0, 0], min: 0, max: 0 }

const sliderReducer = (state = defaultState, action) => {
    switch (action.type) {
        case 'SET_SLIDER_VALUE':
            return { value: action.payload.newValue, min: state.min, max: state.max };
        case 'SET_SLIDER':
            const { value, min, max } = action.payload;
            return { value, min, max }
        case 'RESET_MIN':
            return { ...state, value: [state.min, state.value[1]] };
        case 'RESET_MAX':
            return { ...state, value: [state.value[0], state.max] };
        default:
            return state;
    }
}

export default sliderReducer;