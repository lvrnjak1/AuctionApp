import React, { useEffect, useCallback, useState } from 'react';
import "components/profile/profile.scss";
import "components/settings/settings.scss";
import { deleteRequest, getRequest } from 'http/requests';
import { updateMessage } from 'util/info_div_util';
import { getAuthorizationConfig, logoutUser } from 'util/auth/auth';
import { USERS_ENDPOINT, USER_PROFILE_ENDPOINT } from 'http/endpoints';
import ConfirmationDialog from 'components/confirmation_dialog/confirmationDialog';
import { useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { resetLoggedIn } from 'state/actions/loggedInActions';

function Settings() {
    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [dialogOpen, setDialogOpen] = useState(false);
    const history = useHistory();
    const dispatch = useDispatch();

    const setUserData = useCallback((userData) => {
        setEmail(userData.email);
        setPhoneNumber(userData.phoneNumber);
    }, [])

    useEffect(() => {
        let isSubscribed = true;
        async function getUserProfile() {
            await getRequest(USER_PROFILE_ENDPOINT,
                {},
                (response) => { if (isSubscribed) setUserData(response.data) },
                () => { if (isSubscribed) updateMessage("Something went wrong, try reloading the page", "error", 3000) },
                getAuthorizationConfig()
            );
        }

        getUserProfile();

        return () => (isSubscribed = false)
    }, [setUserData])

    const openDialog = (e) => {
        e.preventDefault();
        setDialogOpen(true);
    }

    const handleDialogClose = () => {
        setDialogOpen(false);
    }

    const handleSuccessfulDeactivate = () => {
        logoutUser();
        dispatch(resetLoggedIn());
        history.push("/login", {
            message: "You account has been deactivated. We are sorry to see you go!",
            messageClass: "success"
        });
    }

    const handleDeactivateAccount = async () => {
        setDialogOpen(false);
        await deleteRequest(USERS_ENDPOINT,
            {},
            handleSuccessfulDeactivate,
            () => updateMessage("Something went wrong. Try this again", "error"),
            getAuthorizationConfig()
        );
    }

    return (
        <form className="settings">
            <div className="form">
                <p className="form-title">Contact information</p>
                <div className="form-content">
                    <p>This information can be edited on your profile.</p>
                    <p>Email<span className="purple-text">{email}</span></p>
                    <p>Phone<span className="purple-text">{phoneNumber || "none"}</span></p>
                </div>
            </div>

            <div className="form">
                <p className="form-title">Account</p>
                <div className="form-content">
                    <p>Do you want to deactivate your account?</p>
                    <button className="btn deactivate-btn" onClick={openDialog}>Deactivate</button>
                </div>
            </div>
            <ConfirmationDialog open={dialogOpen} onClose={handleDialogClose} onOk={handleDeactivateAccount} />
        </form>
    );
}

export default Settings;
