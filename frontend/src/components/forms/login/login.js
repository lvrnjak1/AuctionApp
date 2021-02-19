import React from 'react';
import "components/forms/forms.scss";
import "components/forms/login/login.scss";

function Login(props) {
    return (
        <form className="register-form">
            <p className="title">LOGIN</p>
            <div className="form-content">
                <label>Enter email</label>
                <input type="email" />
                <label>Password</label>
                <input type="password" />
                <button type="submit">LOGIN</button>
            </div>
        </form>
    );
}

export default Login;
