import React, { useState, useEffect, useCallback } from 'react';
import PhoneInput from 'react-phone-number-input';
import 'react-phone-number-input/style.css'
import "components/forms/forms.scss";
import "components/profile/profile.scss";
import { emailRegex, creditCardRegex, cvcRegex } from 'util/validators';
import ExpirationDatePicker from "components/profile/expirationDatePicker";
import { getRequest, patchRequest, uploadFormData } from 'http/requests';
import { UPLOAD_IMAGE_ENDPOINT, USER_PROFILE_ENDPOINT, USER_CARD_INFO_ENDPOINT } from 'http/endpoints';
import { getAuthorizationConfig } from 'util/auth/auth';
import { dateToYMD, getMaxBirthdate } from 'util/dateTimeService';
import { updateMessage } from 'util/info_div_util';
import Loader from 'react-loader-spinner';
import CustomImage from 'components/image/image';

function Profile() {
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [email, setEmail] = useState("");
    const [gender, setGender] = useState("");
    const [dateOfBirth, setDateOfBirth] = useState(null);
    const [phoneNumber, setPhoneNumber] = useState("");
    const [photo, setPhoto] = useState("");
    const [currentUserData, setCurrentUserData] = useState();
    const [uploading, setUploading] = useState(false);

    const [cardInfo, setCardInfo] = useState({
        nameOnCard: "",
        cardNumber: "",
        expirationMonth: "",
        expirationYear: "",
        cvc: ""
    });

    const isCardDetailsFilled = () => {
        return Object.values(cardInfo).some(val => val !== "");
    }

    const isCardInfoChanged = () => {
        if (currentUserData.cardDetails === null && isCardDetailsFilled()) return true;
        else if (currentUserData.cardDetails !== null) {
            return !Object.keys(cardInfo).every(key => {
                return cardInfo[key].toString() === currentUserData.cardDetails[key].toString();
            });
        }
    }

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

    const setCardData = useCallback(({ nameOnCard, cardNumber, cvc, expirationMonth, expirationYear }) => {
        setCardInfo({
            nameOnCard,
            cardNumber,
            cvc: cvc.toString(),
            expirationMonth: expirationMonth.toString(),
            expirationYear: expirationYear.toString()
        });
    }, []);

    useEffect(() => {
        let isSubscribed = true;
        async function getUserProfile() {
            await getRequest(USER_PROFILE_ENDPOINT,
                {},
                (response) => { if (isSubscribed) setUserData(response.data) },
                () => { if (isSubscribed) updateMessage("Something went wrong, try reloading the page", "error", 3000) },
                getAuthorizationConfig()
            );
        }

        getUserProfile();

        return () => (isSubscribed = false)
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

    const buildCardPatchBody = () => {
        const { nameOnCard, cardNumber, cvc, expirationYear, expirationMonth } = cardInfo;
        return {
            nameOnCard,
            cardNumber,
            cvc: parseInt(cvc),
            expirationMonth: parseInt(expirationMonth),
            expirationYear: parseInt(expirationYear)
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        window.scrollTo({ top: 0, behavior: 'smooth' });
        const patchBody = buildPatchBody();
        await patchRequest(USER_PROFILE_ENDPOINT, patchBody, {},
            (response) => {
                setUserData(response.data);
                updateMessage("Profile information is sucessfully updated", "success");
            },
            (error) => {
                updateMessage(error.response.data.message, "error");
                setUserData(currentUserData);
            },
            getAuthorizationConfig());

        const cardInfoChanged = isCardInfoChanged();
        if (!cardInfoChanged) return;
        const cardPatchBody = buildCardPatchBody();
        await patchRequest(USER_CARD_INFO_ENDPOINT, cardPatchBody, {},
            (response) => {
                setCardData(response.data.cardDetails);
                updateMessage("Profile information is sucessfully updated", "success");
            },
            (error) => {
                updateMessage(error.response.data.message, "error");
                setCardData(currentUserData.cardDetails);
            },
            getAuthorizationConfig());
    }

    const handleMonthSelect = (month) => {
        setCardInfo({ ...cardInfo, expirationMonth: month.toString() });
    }

    const handleYearSelect = (year) => {
        setCardInfo({ ...cardInfo, expirationYear: year.toString() });
    }

    const handleImageUpload = async () => {
        setUploading(true);
        const { files } = document.querySelector('input[type="file"]')
        const formData = new FormData();
        formData.append('file', files[0]);
        formData.append('upload_preset', 'upload_preset');
        await uploadFormData(UPLOAD_IMAGE_ENDPOINT,
            formData,
            (response) => { setPhoto(response.data.secure_url); setUploading(false); },
            () => { updateMessage("Something went wrong, try to upload your image again", "error"); setUploading(false) }
        )
    }

    return (
        <form onSubmit={handleSubmit}>
            {uploading ? <div className="loader">
                <Loader type="TailSpin" color="#8367d8" height="20" width="20" />
                <p className="info-message">Please wait while we update your profile.</p>
            </div> : ""}
            <div className="form profile-form">
                <p className="form-title">General</p>
                <div className="form-content">
                    <div className="profile-photo">
                        <div className="image">
                            <CustomImage styles="img" url={photo} height={280} width={200} crop="pad" altText={"Profile avatar"} />
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
                            <option value="OTHER">Other</option>
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
                                required={isCardDetailsFilled()}
                                type="text"
                                placeholder="e.g. Adam Smith"
                                value={cardInfo.nameOnCard}
                                pattern="([a-zA-Z]+\s?\b){2,}"
                                onChange={e => setCardInfo({ ...cardInfo, nameOnCard: e.target.value })} />
                        </div>
                        <div className="input-label-group">
                            <label>Card Number</label>
                            <input
                                className="input"
                                required={isCardDetailsFilled()}
                                type="text"
                                placeholder="e.g. 4242 4242 4242 4242"
                                value={cardInfo.cardNumber}
                                pattern={creditCardRegex}
                                onChange={e => setCardInfo({ ...cardInfo, cardNumber: e.target.value })} />
                        </div>
                    </div>
                    <div className="flex-form-group">
                        <ExpirationDatePicker
                            required={isCardDetailsFilled()}
                            month={cardInfo.expirationMonth}
                            year={cardInfo.expirationYear}
                            handleMonthSelect={handleMonthSelect}
                            handleYearSelect={handleYearSelect} />
                        <div className="input-label-group">
                            <label>CVC/CW</label>
                            <input
                                className="input"
                                required={isCardDetailsFilled()}
                                type="text"
                                placeholder="e.g. 1234"
                                value={cardInfo.cvc}
                                pattern={cvcRegex}
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
