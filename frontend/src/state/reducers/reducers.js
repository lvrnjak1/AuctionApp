import { combineReducers } from "redux";
import infoMessageReducer from "state/reducers/infoMessageReducer";
import userReducer from "state/reducers/userReducer";

const rootReducer = combineReducers({
    infoMessage: infoMessageReducer,
    user: userReducer
});
export default rootReducer;