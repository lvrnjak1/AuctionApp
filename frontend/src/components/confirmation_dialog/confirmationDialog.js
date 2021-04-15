import React from 'react';
import "components/confirmation_dialog/confirmationDialog.scss";
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Slide } from '@material-ui/core';

const Transition = React.forwardRef(function Transition(props, ref) {
    return <Slide direction="up" ref={ref} {...props} />;
});

function ConfirmationDialog(props) {
    return (
        <div>
            <Dialog
                open={props.open}
                TransitionComponent={Transition}
                keepMounted
                onClose={props.onClose}
                aria-labelledby="alert-dialog-slide-title"
                aria-describedby="alert-dialog-slide-description"
            >
                <DialogTitle id="alert-dialog-slide-title">{"Do you want to deactivate your account?"}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-slide-description">
                        If you choose to do this, you will temporarily deactivate your account. Your data won't be deleted
                        but it will not be shown to other users.
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <button className="dialog-btn confirm-btn" onClick={props.onOk} color="primary"> Deactivate</button>
                    <button className="dialog-btn" onClick={props.onClose} color="primary">Cancel </button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default ConfirmationDialog;
