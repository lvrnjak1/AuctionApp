import React, { useEffect, useState } from 'react';
import "components/forms/forms.scss";
import "components/forms/login/login.scss";
import { useHistory } from 'react-router-dom';
import { LOGIN_ENDPOINT } from 'http/endpoints';
import { postRequest } from 'http/requests';

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const [messageStyleClass, setMessageStyleClass] = useState("");
    const history = useHistory();

    useEffect(() => {
        if (history.location.state) {
            setEmail(history.location.state.email || "");
        }
    }, [history.location.state]);

    const setInfoMessage = (message, className = "") => {
        setMessage(message);
        setMessageStyleClass(className);
    }

    const handleInputChange = (e, setter) => {
        setter(e.target.value);
        setInfoMessage("");
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        const loginBody = {
            email, password
        }

        await postRequest(LOGIN_ENDPOINT,
            loginBody,
            (response) => {
                // console.log(response);
                //save token and user
                history.push("/home");
            },
            (error) => {
                if (error.response) {
                    setInfoMessage(error.response.data.message, "error");
                }
                setInfoMessage("Something went wrong, try that again", "error");
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
                <p className={`${messageStyleClass}`}>{message}</p>
            </div>
        </form>
    );
}

export default Login;
