import { combineReducers } from "redux";
import infoMessageReducer from "state/reducers/infoMessageReducer";
import loggedInReducer from "state/reducers/loggedInReducer";

const rootReducer = combineReducers({
    infoMessage: infoMessageReducer,
    loggedIn: loggedInReducer
});
export default rootReducer;