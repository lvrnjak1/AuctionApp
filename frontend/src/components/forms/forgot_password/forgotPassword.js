import React, { useState } from 'react';
import { postRequest } from "http/requests";
import { FORGOT_ENDPOINT } from 'http/endpoints';
import { updateMessage } from 'util/info_div_util';

function ForgotPassword() {
    const [email, setEmail] = useState("");

    const handleError = (error) => {
        if (error.response && error.response.status === 400) {
            updateMessage(error.response.data.message, "error", 5000);
            console.log(error.response);
        } else {
            updateMessage("Something went wrong, try again later", "error", 5000);
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        await postRequest(
            FORGOT_ENDPOINT,
            { email },
            () => {
                updateMessage(
                    `Reset link has been sent to ${email}. If you don't see the email, check your spam folder or try again.`,
                    "success",
                    5000
                );
            },
            (error) => handleError(error)
        );

        setEmail("");
    }

    const handleInputChange = (e, setter) => {
        setter(e.target.value);
    }

    return (
        <form className="register-form" onSubmit={(e) => handleSubmit(e)}>
            <p className="title">FORGOT PASSWORD</p>
            <div className="form-content">
                <p className="description">Lost your password? Please enter your email address. You will recieve a link to create a new password via email.</p>
                <label>Enter email</label>
                <input
                    required
                    type="email"
                    value={email}
                    onChange={e => handleInputChange(e, setEmail)} />
                <button type="submit">SUBMIT</button>
            </div>
        </form>
    );
}

export default ForgotPassword;
