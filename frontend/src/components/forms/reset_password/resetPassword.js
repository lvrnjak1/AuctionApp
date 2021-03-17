import React, { useEffect, useState } from 'react';
import "components/forms/forms.scss";
import "components/forms/login/login.scss";
import { useHistory, useParams } from 'react-router-dom';
import { getRequest, patchRequest } from 'http/requests';
import { CHECK_OTP_TOKEN_ENDPOINT, RESET_PW_ENDPOINT } from 'http/endpoints';
import { updateMessage } from 'util/info_div_util';

function ResetPassword() {
    const history = useHistory();
    const [password, setPassword] = useState("");
    const { token } = useParams();
    const [tokenValid, setTokenValid] = useState(false);

    useEffect(() => {
        const checkToken = async function () {
            await getRequest(CHECK_OTP_TOKEN_ENDPOINT, { token }, () => setTokenValid(true), () => history.push("/404"));
        }
        checkToken();
    }, [history, token]);


    const handleInputChange = (e, setter) => {
        setter(e.target.value);
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        await patchRequest(
            RESET_PW_ENDPOINT,
            { password },
            { token },
            () => {
                updateMessage("Password successfully changed! Redirecting to login page...", "success");
                setTimeout(() => history.push("/login"), 3000);
            },
            () => {
                updateMessage("Something went wrong, try again", "error");
                setTimeout(() => history.push("/password/forgot"), 3000);
            }
        );

        setPassword("");
    }

    return (
        tokenValid && <form className="register-form" onSubmit={handleSubmit}>
            <p className="title">RESET PASSWORD</p>
            <div className="form-content">
                <label>Enter password</label>
                <input
                    required
                    minLength={5}
                    type="password"
                    value={password}
                    onChange={e => handleInputChange(e, setPassword)} />
                <button type="submit">SUBMIT</button>
            </div>
        </form>
    );
}

export default ResetPassword;
