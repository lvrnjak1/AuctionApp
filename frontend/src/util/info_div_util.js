import { resetInfoMessage, setInfoMessage } from "state/actions/infoMessageActions";
import store from "state/store";

const updateMessage = (message, className, timeout = 3000) => {
    store.dispatch(setInfoMessage(message, className));
    setTimeout(() => store.dispatch(resetInfoMessage()), timeout);
}

export {
    updateMessage
}