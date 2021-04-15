import React, { useState } from 'react';
import { Step, StepLabel, Stepper } from '@material-ui/core';
import StepOne from './step1';
import StepTwo from './step2';
import "components/seller/seller.scss";
import "components/seller/new_item/newItem.scss";
import { postRequest, uploadMultipleImages } from 'http/requests';
import { AUCTIONS_ENDPOINT } from 'http/endpoints';
import { updateMessage } from 'util/info_div_util';
import { getAuthorizationConfig } from 'util/auth/auth';
import { useHistory } from 'react-router-dom';
import { getTomorrow } from 'util/dateTimeService';
import Loader from 'react-loader-spinner';

function NewItem() {
    const [activeStep, setActiveStep] = useState(0);
    const [productName, setProductName] = useState("");
    const [category, setCategory] = useState(1);
    const [subcategory, setSubcategory] = useState(7);
    const [productDescription, setProductDescription] = useState("");
    const [startPrice, setStartPrice] = useState(0);
    const [startDateTime, setStartDateTime] = useState(getTomorrow());
    const [endDateTime, setEndDateTime] = useState(getTomorrow());
    const [images, setImages] = useState([]);
    const history = useHistory();
    const [uploading, setUploading] = useState(false);

    const getSteps = () => {
        return [<StepOne
            productName={productName} setProductName={setProductName}
            category={category} setCategory={setCategory}
            subcategory={subcategory} setSubcategory={setSubcategory}
            productDescription={productDescription} setProductDescription={setProductDescription}
            images={images} setImages={setImages}
            handleNext={handleNext} handleBack={handleBack} activeStep={activeStep} maxSteps={2}
        />,
        <StepTwo
            startPrice={startPrice} setStartPrice={setStartPrice}
            startDateTime={startDateTime} setStartDateTime={setStartDateTime}
            endDateTime={endDateTime} setEndDateTime={setEndDateTime}
            handleNext={handleNext} handleBack={handleBack} activeStep={activeStep} maxSteps={2}
        />]
    }

    const handleSubmit = async () => {
        if (images.length < 3) {
            updateMessage("Upload at least 3 images of your product", "error");
            return;
        }
        setUploading(true);
        await uploadMultipleImages(images, async (responses) => {
            const body = {
                startDateTime: new Date(startDateTime).getTime(),
                endDateTime: new Date(endDateTime).getTime(),
                startPrice,
                product: {
                    name: productName,
                    description: productDescription === "" ? null : productDescription,
                    categoryId: subcategory !== "" ? subcategory : category,
                    images: responses.map(response => response.data.secure_url)
                },
            }
            await postRequest(AUCTIONS_ENDPOINT, body,
                () => {
                    updateMessage("Item sucessfully added", "success");
                    setUploading(false);
                    history.push("/seller");
                },
                () => {
                    updateMessage("Something went wrong, try again", "error");
                    setUploading(false);
                },
                getAuthorizationConfig())
        });
    }

    const handleNext = (e) => {
        e.preventDefault();
        if (activeStep === getSteps().length - 1) {
            handleSubmit();
        } else {
            setActiveStep((prevActiveStep) => prevActiveStep + 1);
        }
    };

    const handleBack = (e) => {
        e.preventDefault();
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    return (
        <div className="seller new-item">
            <Stepper className="stepper" activeStep={activeStep} >
                {getSteps().map((step, index) => {
                    return <Step key={index}><StepLabel /></Step>
                })}
            </Stepper>
            <div>
                {getSteps()[activeStep]}
            </div>
            {uploading ? <div className="loader">
                <Loader type="TailSpin" color="#8367d8" height="20" width="20" />
                <p className="info-message">Please wait while we upload your item. This may take a couple of minutes.</p>
            </div> : ""}
        </div>
    );
}

export default NewItem;


