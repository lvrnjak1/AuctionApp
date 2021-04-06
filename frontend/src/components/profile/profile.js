import React, { useEffect, useState } from 'react';
import PhoneInput from 'react-phone-number-input';
import 'react-phone-number-input/style.css'
import "components/forms/forms.scss";
import "components/profile/profile.scss";
import { emailRegex } from 'util/emailValidator';
import { FormControlLabel, Radio, RadioGroup } from '@material-ui/core';
import ExpirationDatePicker from "components/profile/expirationDatePicker";

function Profile() {
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [email, setEmail] = useState("");
    const [gender, setGender] = useState("M");
    const [dateOfBirth, setDateOfBirth] = useState(new Date());
    const [phoneNumber, setPhoneNumber] = useState("");
    const [photo, setPhoto] = useState("");

    const [cardInfo, setCardInfo] = useState({
        payPal: false,
        nameOnCard: "",
        cardNubmer: "",
        expirationDate: null,
        cvc: ""
    });

    const handleInputChange = (e, setter) => {
        setter(e.target.value);
        // dispatch(resetInfoMessage());
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(name);
        console.log(surname);
        console.log(email);
        console.log(gender);
        console.log(dateOfBirth);
        console.log(phoneNumber);
        console.log(cardInfo);
    }

    const handleCheckBoxChange = () => {
        setCardInfo({ ...cardInfo, payPal: !cardInfo.payPal });
    }

    const handleMonthSelect = (month) => {
        setCardInfo({ ...cardInfo, expirationDate: { ...cardInfo.expirationDate, month: month } });
    }

    const handleYearSelect = (year) => {
        setCardInfo({ ...cardInfo, expirationDate: { ...cardInfo.expirationDate, year: year } });
    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="form profile-form">
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
                            pattern="^[a-zA-Z]{2,30}$"
                            title="First name should have between 2 and 30 letters"
                            onChange={e => handleInputChange(e, setName)} />

                        <label>Last name</label>
                        <input
                            className="input"
                            required
                            type="text"
                            value={surname}
                            pattern="^[a-zA-Z]{2,30}$"
                            title="Last name should have between 2 and 30 letters"
                            onChange={e => handleInputChange(e, setSurname)} />

                        <label>I am</label>
                        <select className="input select" value={gender} onChange={e => handleInputChange(e, setGender)}>
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
            <div className="form card-info-form">
                <p className="form-title">Card information</p>
                <div className="form-content">
                    <RadioGroup>
                        <FormControlLabel
                            control={<Radio
                                checked={cardInfo.payPal}
                                onChange={handleCheckBoxChange}
                                name="paypal" />
                            }
                            label="Pay Pal"
                        />
                        <FormControlLabel
                            control={<Radio
                                checked={!cardInfo.payPal}
                                onChange={handleCheckBoxChange}
                                name="creditcard" />
                            }
                            label="Credit card"
                        />
                    </RadioGroup>
                    <div className="credit-cards">
                        <p>We accept the following credit cards:</p>
                        <img src={process.env.PUBLIC_URL + '/images/visa.jpg'} alt="visa logo" className="credit-card-logo" />
                        <img src={process.env.PUBLIC_URL + '/images/mastercard.jpg'} alt="mastercard logo" className="credit-card-logo" />
                        <img src={process.env.PUBLIC_URL + '/images/maestro.jpg'} alt="maestro logo" className="credit-card-logo" />
                        <img src={process.env.PUBLIC_URL + '/images/american.jpg'} alt="american logo" className="credit-card-logo" />
                    </div>
                    <div className="flex-form-group">
                        <div className="input-label-group">
                            <label>Name On Card</label>
                            <input
                                className="input"
                                required
                                type="text"
                                placeholder="e.g. Adam Smith"
                                value={cardInfo.nameOnCard}
                                pattern="^[a-z]+(?: [a-z]+)+$"
                                title="Name on card should have at least two words"
                                onChange={e => setCardInfo({ ...cardInfo, nameOnCard: e.target.value })} />
                        </div>
                        <div className="input-label-group">
                            <label>Card Number</label>
                            <input
                                className="input"
                                required
                                type="text"
                                placeholder="e.g. 4242 4242 4242 4242"
                                value={cardInfo.cardNubmer}
                                onChange={e => setCardInfo({ ...cardInfo, cardNubmer: e.target.value })} />
                        </div>
                    </div>
                    <div className="flex-form-group">
                        <ExpirationDatePicker
                            handleMonthSelect={handleMonthSelect}
                            handleYearSelect={handleYearSelect} />
                        <div className="input-label-group">
                            <label>CVC/CW</label>
                            <input
                                className="input"
                                required
                                type="text"
                                placeholder="e.g. 1234"
                                value={cardInfo.cvc}
                                onChange={e => setCardInfo({ ...cardInfo, cvc: e.target.value })} />
                        </div>
                    </div>
                </div>
            </div>
            {/* <div className="form optional-form">
                <p className="form-title">Optional</p>
                <div className="form-content">
                    <p>Address</p>
                    <label>Street</label>
                    <input
                        className="input"
                        required
                        type="text"
                        placeholder="e.g. 1234"
                        value={cardInfo.cvc}
                        onChange={e => setCardInfo({ ...cardInfo, cvc: e.target.value })} />
                </div>
            </div> */}
            <button className="btn" type="submit">{"Save info"}</button>
        </form>
    );
}

export default Profile;
