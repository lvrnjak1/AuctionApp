import React, { useEffect, useState } from 'react';
import "components/forms/forms.scss";
import "components/forms/login/login.scss";
import { useHistory } from 'react-router-dom';
import { LOGIN_ENDPOINT } from 'http/endpoints';
import { postRequest } from 'http/requests';
import { useDispatch } from 'react-redux';
import { set as setInfoMessage, reset as resetInfoMessage } from 'state/actions/infoMessageActions';
import { set as setUser } from 'state/actions/userActions';

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
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

    const resetFormFields = () => {
        setEmail("");
        setPassword("");
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        const loginBody = {
            email, password
        }

        await postRequest(LOGIN_ENDPOINT,
            loginBody,
            (response) => {
                //save token and user
                dispatch(setUser(response.data.user));
                history.push("/home");
            },
            (error) => {
                resetFormFields();
                if (error.response) {
                    dispatch(setInfoMessage(error.response.data.message, "error"))
                } else {
                    dispatch(setInfoMessage("Something went wrong, try that again", "error"));
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

                <button type="submit">LOGIN</button>
            </div>
        </form>
    );
}

export default Login;
