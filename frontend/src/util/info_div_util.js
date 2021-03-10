import { resetInfoMessage, setInfoMessage } from "state/actions/infoMessageActions";
import store from "state/store";

const updateMessage = (message, className) => {
    store.dispatch(setInfoMessage(message, className));
    setTimeout(() => store.dispatch(resetInfoMessage()), 3000);
}

export {
    updateMessage
}