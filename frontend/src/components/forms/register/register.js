import React from 'react';
import { NavLink } from 'react-router-dom';
import "components/forms/forms.scss";

function Register() {
    return (
        <form className="register-form">
            <p className="title">REGISTER</p>
            <div className="form-content">
                <label for="name">First name</label>
                <input type="text" id="name" />
                <label>Last name</label>
                <input type="text" />
                <label>Email</label>
                <input type="email" />
                <label>Password</label>
                <input type="password" />
                <button type="submit">REGISTER</button>
                <p>Already have an account? <NavLink to="/login" className="link">Login</NavLink></p>
            </div>
        </form>
    );
}

export default Register;
