import { combineReducers } from "redux";
import infoMessageReducer from "state/reducers/infoMessageReducer";
import loggedInReducer from "state/reducers/loggedInReducer";
import asyncTaskInProgressReducer from "state/reducers/asyncTaskInProgressReducer";
import currentCategoryReducer from "./currentCategoryReducer";
import filterParamsReducer from "./filterParamsReducer";
import categoriesReducer from "./categoriesReducer";
import searchReducer from "./searchReducer";
import filterChangeReducer from "./filterChangeReducer";

const rootReducer = combineReducers({
    infoMessage: infoMessageReducer,
    loggedIn: loggedInReducer,
    asyncInProgress: asyncTaskInProgressReducer,
    currentCategory: currentCategoryReducer,
    filterParams: filterParamsReducer,
    categories: categoriesReducer,
    searchCriteria: searchReducer,
    filterChanged: filterChangeReducer
});
export default rootReducer;