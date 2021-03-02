import { combineReducers } from "redux";
import infoMessageReducer from "state/reducers/infoMessageReducer";
import loggedInReducer from "state/reducers/loggedInReducer";
import asyncTaskInProgressReducer from "state/reducers/asyncTaskInProgressReducer";

const rootReducer = combineReducers({
    infoMessage: infoMessageReducer,
    loggedIn: loggedInReducer,
    asyncInProgress: asyncTaskInProgressReducer
});
export default rootReducer;