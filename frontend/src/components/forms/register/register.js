import React, { useState } from 'react';
import { NavLink, useHistory } from 'react-router-dom';
import "components/forms/forms.scss";
import { REGISTER_ENDPOINT } from 'http/endpoints';
import { postRequest } from 'http/requests';

function Register(props) {
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
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

        const registerBody = {
            name, surname, email, password
        }

        await postRequest(REGISTER_ENDPOINT,
            registerBody,
            () => { history.push("/login", { email }) },
            (error) => {
                if (error.response) {
                    setInfoMessage(error.response.data.message, "error");
                }
                setInfoMessage("Something went wrong, try that again.", "error");
            });
    }

    return (
        <form className="register-form" onSubmit={handleSubmit}>
            <p className="title">REGISTER</p>
            <div className="form-content">
                <label>First name</label>
                <input
                    required
                    type="text"
                    value={name}
                    onChange={e => handleInputChange(e, setName)} />

                <label>Last name</label>
                <input
                    required
                    type="text"
                    value={surname}
                    onChange={e => handleInputChange(e, setSurname)} />

                <label>Email</label>
                <input
                    required
                    type="email"
                    value={email}
                    onChange={e => handleInputChange(e, setEmail)} />

                <label>Password</label>
                <input
                    required
                    minLength={5}
                    type="password"
                    value={password}
                    onChange={e => handleInputChange(e, setPassword)} />

                <button type="submit">REGISTER</button>
                <p>Already have an account? <NavLink to="/login" className="link">Login</NavLink></p>
                <p className={`${messageStyleClass}`}>{message}</p>
            </div>
        </form>
    );
}

export default Register;
