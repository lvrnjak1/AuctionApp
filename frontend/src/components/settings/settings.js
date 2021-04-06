import React from 'react';
import "components/profile/profile.scss";
import "components/settings/settings.scss";

function Settings() {

    return (
        <form className="settings">
            <div className="form">
                <p className="form-title">Contact information</p>
                <div className="form-content">
                    <p>This information can be edited on your profile.</p>
                    <p>Email<span className="purple-text">lamija.vrnjak@gmail.com</span></p>
                    <p>Phone<span className="purple-text">062 348 811</span></p>
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
