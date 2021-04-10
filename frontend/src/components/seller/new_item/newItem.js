import React, { useState } from 'react';
import { Step, StepLabel, Stepper } from '@material-ui/core';
import StepOne from './step1';
import StepTwo from './step2';
import "components/seller/seller.scss";
import "components/seller/new_item/newItem.scss";
import { postRequest } from 'http/requests';
import { AUCTIONS_ENDPOINT } from 'http/endpoints';
import { updateMessage } from 'util/info_div_util';
import { getAuthorizationConfig } from 'util/auth/auth';
import { useHistory } from 'react-router-dom';

function NewItem() {
    const [activeStep, setActiveStep] = useState(0);
    const [productName, setProductName] = useState("");
    const [category, setCategory] = useState("");
    const [subcategory, setSubcategory] = useState("");
    const [productDescription, setProductDescription] = useState("");
    const [startPrice, setStartPrice] = useState(0);
    const [startDateTime, setStartDateTime] = useState(new Date());
    const [endDateTime, setEndDateTime] = useState(new Date());
    const history = useHistory();

    const getSteps = () => {
        return [<StepOne
            productName={productName} setProductName={setProductName}
            category={category} setCategory={setCategory}
            subcategory={subcategory} setSubcategory={setSubcategory}
            productDescription={productDescription} setProductDescription={setProductDescription}
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
        const body = {
            startDateTime: new Date(startDateTime).getTime(),
            endDateTime: new Date(endDateTime).getTime(),
            startPrice,
            product: {
                name: productName,
                description: productDescription === "" ? null : productDescription,
                categoryId: subcategory !== "" ? subcategory : category
            }
        }
        await postRequest(AUCTIONS_ENDPOINT, body,
            () => {
                updateMessage("Item sucessfully added", "success");
                history.push("/seller");
            },
            () => {
                updateMessage("Something went wrong, try again", "error");
            },
            getAuthorizationConfig())
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
            {getSteps()[activeStep]}
        </div>
    );
}

export default NewItem;


