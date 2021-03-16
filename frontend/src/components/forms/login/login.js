import React, { useEffect, useState } from 'react';
import "components/forms/forms.scss";
import "components/forms/login/login.scss";
import { NavLink, useHistory } from 'react-router-dom';
import { LOGIN_ENDPOINT } from 'http/endpoints';
import { postRequest } from 'http/requests';
import { useDispatch } from 'react-redux';
import { resetInfoMessage } from 'state/actions/infoMessageActions';
import { setLoggedIn } from 'state/actions/loggedInActions';
import { loginUser } from 'util/auth/auth';
import { Checkbox, FormControlLabel } from '@material-ui/core';
import { updateMessage } from 'util/info_div_util';

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [rememberMe, setRememberMe] = useState(false);
    const history = useHistory();
    const dispatch = useDispatch();

    useEffect(() => {
        if (history.location.state) {
            setEmail(history.location.state.email || "");
        }
    }, [history.location.state]);


    const handleInputChange = (e, setter) => {
        setter(e.target.value);
        dispatch(resetInfoMessage());
    }

    const handleCheckBoxChange = e => {
        setRememberMe(!rememberMe);
    }

    const resetFormFields = () => {
        setEmail("");
        setPassword("");
        setRememberMe(false);
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        const loginBody = {
            email, password
        }

        await postRequest(LOGIN_ENDPOINT,
            loginBody,
            (response) => {
                loginUser(response.data.user, response.data.token, rememberMe);
                dispatch(setLoggedIn());
                history.push("/home");
            },
            (error) => {
                resetFormFields();
                if (error.response) {
                    updateMessage(error.response.data.message, "error");
                } else {
                    updateMessage("Try reloading the page.", "error");
                }
            });
    }

    return (
        <form className="register-form" onSubmit={handleSubmit}>
            <p className="title">LOGIN</p>
            <div className="form-content">
                <label>Enter email</label>
                <input
                    required
                    type="email"
                    value={email}
                    onChange={e => handleInputChange(e, setEmail)} />
                <label>Password</label>

                <input
                    required
                    type="password"
                    value={password}
                    onChange={e => handleInputChange(e, setPassword)} />

                <FormControlLabel
                    control={<Checkbox checked={rememberMe} onChange={handleCheckBoxChange} name="rememberMe" />}
                    label="Remember me"
                />
                <button type="submit">LOGIN</button>
                <p><NavLink to="#" className="link">Forgot password?</NavLink></p>
            </div>
        </form>
    );
}

export default Login;
