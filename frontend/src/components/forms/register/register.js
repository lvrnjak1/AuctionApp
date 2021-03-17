import React, { useState } from 'react';
import { NavLink, useHistory } from 'react-router-dom';
import "components/forms/forms.scss";
import { REGISTER_ENDPOINT } from 'http/endpoints';
import { postRequest } from 'http/requests';
import { useDispatch } from 'react-redux';
import { resetInfoMessage } from 'state/actions/infoMessageActions';
import { updateMessage } from 'util/info_div_util';
import { loginUser } from 'util/auth/auth';
import { setLoggedIn } from 'state/actions/loggedInActions';
import { emailRegex } from 'util/emailValidator';

function Register() {
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const history = useHistory();
    const dispatch = useDispatch();

    const handleInputChange = (e, setter) => {
        setter(e.target.value);
        dispatch(resetInfoMessage());
    }

    const resetFormFields = () => {
        setName("");
        setSurname("");
        setEmail("");
        setPassword("");
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        const registerBody = {
            name, surname, email, password
        }

        await postRequest(REGISTER_ENDPOINT,
            registerBody,
            (response) => {
                updateMessage("Registration was successful. Happy bidding.", "success");
                setTimeout(() => {
                    loginUser(response.data.user, response.data.token, false);
                    dispatch(setLoggedIn());
                    history.push("/home");
                }, 2000);
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
                    pattern={emailRegex}
                    title="This email doesn't have a valid email address format"
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
            </div>
        </form>
    );
}

export default Register;
