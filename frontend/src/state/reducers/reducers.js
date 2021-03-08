import { combineReducers } from "redux";
import infoMessageReducer from "state/reducers/infoMessageReducer";
import loggedInReducer from "state/reducers/loggedInReducer";
import asyncTaskInProgressReducer from "state/reducers/asyncTaskInProgressReducer";
import currentCategoryReducer from "./currentCategoryReducer";

const rootReducer = combineReducers({
    infoMessage: infoMessageReducer,
    loggedIn: loggedInReducer,
    asyncInProgress: asyncTaskInProgressReducer,
    currentCategory: currentCategoryReducer
});
export default rootReducer;