import React, { useState, useEffect, useCallback } from 'react';
import PhoneInput from 'react-phone-number-input';
import 'react-phone-number-input/style.css'
import "components/forms/forms.scss";
import "components/profile/profile.scss";
import { emailRegex } from 'util/emailValidator';
import { FormControlLabel, Input, Radio, RadioGroup } from '@material-ui/core';
import ExpirationDatePicker from "components/profile/expirationDatePicker";
import { getRequest, patchRequest, uploadFormData } from 'http/requests';
import { UPLOAD_IMAGE_ENDPOINT, USER_PROFILE_ENDPOINT } from 'http/endpoints';
import { getAuthorizationConfig } from 'util/auth/auth';
import Image from 'cloudinary-react/lib/components/Image';
import Transformation from 'cloudinary-react/lib/components/Transformation';
import { getPublicId } from 'util/images_util';
import { dateToYMD, getMaxBirthdate } from 'util/dateTimeService';
import { updateMessage } from 'util/info_div_util';

function Profile() {
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [email, setEmail] = useState("");
    const [gender, setGender] = useState("");
    const [dateOfBirth, setDateOfBirth] = useState(null);
    const [phoneNumber, setPhoneNumber] = useState("");
    const [photo, setPhoto] = useState("");
    const [currentUserData, setCurrentUserData] = useState();

    const [cardInfo, setCardInfo] = useState({
        payPal: false,
        nameOnCard: "",
        cardNubmer: "",
        expirationDate: null,
        cvc: ""
    });

    const setUserData = useCallback((userData) => {
        setCurrentUserData(userData);
        setName(userData.name);
        setSurname(userData.surname);
        setEmail(userData.email);
        setPhoto(userData.profilePhotoUrl);
        if (userData.gender !== null) setGender(userData.gender)
        if (userData.dateOfBirth !== null) setDateOfBirth(new Date(userData.dateOfBirth));
        setPhoneNumber(userData.phoneNumber);
    }, []);

    useEffect(() => {
        async function getUserProfile() {
            await getRequest(USER_PROFILE_ENDPOINT,
                {},
                (response) => { setUserData(response.data) },
                () => { updateMessage("Something went wrong, try reloading the page", 3000) },
                getAuthorizationConfig()
            );
        }

        getUserProfile();
    }, [setUserData])

    const handleInputChange = (e, setter) => {
        setter(e.target.value);
    }

    const buildPatchBody = () => {
        const body = {}
        if (name !== currentUserData.name) body["name"] = name;
        if (surname !== currentUserData.surname) body["surname"] = surname;
        if (gender !== currentUserData.gender) body["gender"] = gender !== "" ? gender : null;
        if (email !== currentUserData.email) body["email"] = email;
        if (dateOfBirth !== currentUserData.dateOfBirth) body["dateOfBirth"] = dateOfBirth.getTime();
        if (phoneNumber !== currentUserData.phoneNumber) body["phoneNumber"] = phoneNumber;
        if (photo !== currentUserData.photo) body["profilePhotoUrl"] = photo;
        return body;
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        const patchBody = buildPatchBody();
        await patchRequest(USER_PROFILE_ENDPOINT, patchBody, {},
            (response) => setUserData(response.data),
            (error) => {
                updateMessage(error.response.data.message, "error");
                setUserData(currentUserData);
            },
            getAuthorizationConfig())
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

    const handleImageUpload = async () => {
        const { files } = document.querySelector('input[type="file"]')
        const formData = new FormData();
        formData.append('file', files[0]);
        formData.append('upload_preset', 'upload_preset');
        await uploadFormData(UPLOAD_IMAGE_ENDPOINT,
            formData,
            (response) => { setPhoto(response.data.secure_url) },
            () => updateMessage("Something went wrong, try to upload your image again", "error")
        )
    }

    return (
        <form onSubmit={handleSubmit}>
            <div className="form profile-form">
                <p className="form-title">General</p>
                <div className="form-content">
                    <div className="profile-photo">
                        <div className="image">
                            {photo !== null ? <Image className="img" cloudName="lvrnjak" publicId={getPublicId(photo)} >
                                <Transformation height={280} width={200} crop="pad" quality="auto" flags="lossy" />
                            </Image> : <img
                                    src={process.env.PUBLIC_URL + '/images/image-placeholder.png'}
                                    alt="profile"
                                    className="img"
                                />
                            }
                        </div>
                        <div className="file-button-group">
                            <input type="file" name="file" id="file" className="input-file" onChange={handleImageUpload} />
                            <label htmlFor="file" className="btn file-btn">Choose a photo</label>
                            {photo != null && <button className="btn file-btn" onClick={() => setPhoto(null)}>
                                Remove profile photo
                            </button>
                            }
                        </div>
                    </div>
                    <div className="account-details">
                        <label>First name<span className="req">*</span></label>
                        <input
                            className="input"
                            required
                            type="text"
                            value={name}
                            pattern="^[a-zA-Z]{2,30}$"
                            onChange={e => handleInputChange(e, setName)} />

                        <label>Last name<span className="req">*</span></label>
                        <input
                            className="input"
                            required
                            type="text"
                            value={surname}
                            pattern="^[a-zA-Z]{2,30}$"
                            onChange={e => handleInputChange(e, setSurname)} />

                        <label>I am</label>
                        <select className="input select" value={gender} onChange={e => handleInputChange(e, setGender)}>
                            <option value=""></option>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                        </select>

                        <label>Date of birth</label>
                        <input
                            className="input"
                            type="date"
                            value={dateOfBirth != null ? dateToYMD(dateOfBirth) : ""}
                            max={getMaxBirthdate()}
                            onChange={e => setDateOfBirth(new Date(e.target.value))} />

                        <label>Phone number</label>
                        <PhoneInput
                            className="phone-input"
                            international
                            value={phoneNumber}
                            onChange={setPhoneNumber} />

                        <label>Email<span className="req">*</span></label>
                        <input
                            className="input"
                            required
                            type="email"
                            pattern={emailRegex}
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
                                // required
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
                                // required
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
                                // required
                                type="text"
                                placeholder="e.g. 1234"
                                value={cardInfo.cvc}
                                onChange={e => setCardInfo({ ...cardInfo, cvc: e.target.value })} />
                        </div>
                    </div>
                </div>
            </div>
            <button className="btn" type="submit">{"Save info"}</button>
        </form >
    );
}

export default Profile;
