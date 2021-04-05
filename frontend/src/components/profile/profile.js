import React, { useEffect, useState } from 'react';
import PhoneInput from 'react-phone-number-input';
import 'react-phone-number-input/style.css'
import "components/forms/forms.scss";
import "components/profile/profile.scss";
import { emailRegex } from 'util/emailValidator';

function Profile() {
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [email, setEmail] = useState("");
    const [gender, setGender] = useState("M");
    const [dateOfBirth, setDateOfBirth] = useState(new Date());
    const [phoneNumber, setPhoneNumber] = useState("");
    const [photo, setPhoto] = useState("");

    const handleInputChange = (e, setter) => {
        setter(e.target.value);
        // dispatch(resetInfoMessage());
    }

    const handleSubmit = () => {
        console.log(name);
        console.log(surname);
        console.log(email);
        console.log(gender);
        console.log(dateOfBirth);
        console.log(phoneNumber);

    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="form profile-form ">
                <p className="form-title">General</p>
                <div className="form-content">
                    <div className="profile-photo">
                        <div className="image"></div>
                        <button className="btn file-btn">
                            Change photo
                        </button>
                    </div>
                    <div className="account-details">
                        <label>First name</label>
                        <input
                            className="input"
                            required
                            type="text"
                            value={name}
                            onChange={e => handleInputChange(e, setName)} />

                        <label>Last name</label>
                        <input
                            className="input"
                            required
                            type="text"
                            value={surname}
                            onChange={e => handleInputChange(e, setSurname)} />

                        <label>I am</label>
                        <select className="input gender-select" value={gender} onChange={e => handleInputChange(e, setGender)}>
                            <option value="M">Male</option>
                            <option value="F">Female</option>
                        </select>

                        <label>Date of birth</label>
                        <input
                            className="input"
                            type="date"
                            value={dateOfBirth}
                            onChange={e => handleInputChange(e, setDateOfBirth)} />

                        <label>Phone number</label>
                        <PhoneInput
                            className="phone-input"
                            international
                            value={phoneNumber}
                            onChange={setPhoneNumber} />

                        <label>Email</label>
                        <input
                            className="input"
                            required
                            type="email"
                            pattern={emailRegex}
                            title="This email doesn't have a valid email address format"
                            value={email}
                            onChange={e => handleInputChange(e, setEmail)} />
                    </div>
                </div>
            </div>
            <button className="btn" type="submit">{"Save info"}</button>
        </form>
    );
}

export default Profile;
