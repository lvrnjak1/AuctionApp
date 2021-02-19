import React, { useState } from 'react';
import "components/forms/forms.scss";
import "components/forms/login/login.scss";
import { useHistory } from 'react-router-dom';
import axios from 'axios';
import { LOGIN_ENDPOINT } from 'util/endpoints';

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const [messageStyleClass, setMessageStyleClass] = useState("");

    const history = useHistory();

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

        try {
            const response = await axios.post(LOGIN_ENDPOINT, loginBody);
            console.log(response);
            //save token and user
            history.push("/home");
        } catch (error) {
            if (error.response) {
                setInfoMessage(error.response.data.message, "error");
            }
            setInfoMessage("Something went wrong, try that again", "error");
        }
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
