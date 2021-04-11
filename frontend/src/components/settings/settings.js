import React, { useEffect, useCallback, useState } from 'react';
import "components/profile/profile.scss";
import "components/settings/settings.scss";
import { getRequest } from 'http/requests';
import { updateMessage } from 'util/info_div_util';
import { getAuthorizationConfig } from 'util/auth/auth';
import { USER_PROFILE_ENDPOINT } from 'http/endpoints';

function Settings() {
    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");

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
                    <button className="btn deactivate-btn">Deactivate</button>
                </div>
            </div>
        </form>
    );
}

export default Settings;
