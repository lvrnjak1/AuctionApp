import React, { useState } from 'react';

function ResetPassword() {
    const [email, setEmail] = useState("");

    const handleSubmit = () => {

    }

    const handleInputChange = (e, setter) => {
        setter(e.target.value);
    }

    return (
        <form className="register-form" onSubmit={handleSubmit}>
            <p className="title">FORGOT PASSWORD</p>
            <div className="form-content">
                <p className="description">Lost your password? Please enter your email address. You will recieve a link to create a new password via email.</p>
                <label>Enter email</label>
                <input
                    required
                    type="email"
                    value={email}
                    onChange={e => handleInputChange(e, setEmail)} />
                <button type="submit">SUMBIT</button>
            </div>
        </form>
    );
}

export default ResetPassword;
