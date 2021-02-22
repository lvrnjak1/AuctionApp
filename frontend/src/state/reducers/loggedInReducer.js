import { getToken } from "util/auth/auth";

const loggedInReducer = (state = getToken(), action) => {
    switch (action.type) {
        case 'SET_USER':
            return true;
        case 'RESET_USER':
            return false;
        default:
            return state;
    }
}

export default loggedInReducer;