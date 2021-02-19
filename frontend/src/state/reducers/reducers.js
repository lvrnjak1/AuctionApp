import { combineReducers } from "redux";
import infoMessageReducer from "state/reducers/infoMessageReducer";

const rootReducer = combineReducers({ infoMessage: infoMessageReducer });
export default rootReducer;